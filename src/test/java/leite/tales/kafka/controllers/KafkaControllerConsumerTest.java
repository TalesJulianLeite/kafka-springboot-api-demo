package leite.tales.kafka.controllers;


import leite.tales.kafka.services.consumers.KafkaConsumerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.CircuitBreaker;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KafkaControllerConsumerTest {

    @Mock
    private KafkaConsumerService consumerService;

    @Mock
    private CircuitBreaker circuitBreaker;

    @InjectMocks
    private KafkaControllerConsumer controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .slidingWindowSize(5)
                .build();

        circuitBreaker = CircuitBreaker.of("kafkaConsumer", config);
        controller = new KafkaControllerConsumer();
        controller.listener = consumerService;
    }

    @Test
    void read_ShouldReturnOkWhenSuccessful() {
        // Arrange
        String expectedMessage = "test message";
        when(consumerService.consumirMensagem()).thenReturn(expectedMessage);

        // Act
        ResponseEntity<String> response = controller.read();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedMessage, response.getBody());
    }

    @Test
    void read_ShouldThrowExceptionWhenServiceFails() {
        // Arrange
        when(consumerService.consumirMensagem()).thenThrow(new KafkaConsumerException("Consumer error"));

        // Act & Assert
        assertThrows(KafkaConsumerException.class, () -> controller.read());
    }

    @Test
    void read_WithCircuitBreaker_ShouldReturnFallbackWhenOpen() {
        // Arrange
        when(circuitBreaker.tryAcquirePermission()).thenReturn(false);

        // Act
        ResponseEntity<String> response = controller.read();

        // Assert
        assertEquals(503, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Service unavailable"));
    }
}