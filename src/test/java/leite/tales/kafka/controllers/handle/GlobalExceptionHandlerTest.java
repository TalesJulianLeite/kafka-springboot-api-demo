package leite.tales.kafka.controllers.handle;


import leite.tales.kafka.exceptions.KafkaConsumerException;
import leite.tales.kafka.exceptions.KafkaProducerException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleKafkaConsumerException_ShouldReturnInternalServerError() {
        // Arrange
        String errorMessage = "Consumer error";
        KafkaConsumerException ex = new KafkaConsumerException(errorMessage);

        // Act
        ResponseEntity<String> response = handler.handleKafkaConsumerException(ex);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains(errorMessage));
    }

    @Test
    void handleKafkaProducerException_ShouldReturnInternalServerError() {
        // Arrange
        String errorMessage = "Producer error";
        KafkaProducerException ex = new KafkaProducerException(errorMessage);

        // Act
        ResponseEntity<String> response = handler.handleKafkaProducerException(ex);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains(errorMessage));
    }

    @Test
    void handleGenericException_ShouldReturnInternalServerError() {
        // Arrange
        String errorMessage = "Generic error";
        Exception ex = new Exception(errorMessage);

        // Act
        ResponseEntity<String> response = handler.handleGenericException(ex);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains(errorMessage));
    }
}