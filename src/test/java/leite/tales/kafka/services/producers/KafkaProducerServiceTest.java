package leite.tales.kafka.services.producers;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.SettableListenableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaProducerServiceTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private KafkaProducerService producerService;

    private final String testMessage = "test-message";

    @Test
    void sendMessageAsync_Success() throws Exception {
        // Arrange
        RecordMetadata metadata = new RecordMetadata(new TopicPartition("meu-topico", 0), 0L, 0L, 0L, 0L, 0, 0);
        SendResult<String, String> sendResult = new SendResult<>(null, metadata);

        // Criar um ListenableFuture completado
        SettableListenableFuture<SendResult<String, String>> listenableFuture = new SettableListenableFuture<>();
        listenableFuture.set(sendResult);

        Mockito.<CompletableFuture<SendResult<String, String>>>when(kafkaTemplate.send(anyString(), anyString())).thenReturn(listenableFuture);

        // Act
        CompletableFuture<String> result = producerService.sendMessageAsync(testMessage);

        // Assert
        assertNotNull(result.get(1, TimeUnit.SECONDS)); // Timeout para evitar bloqueio

    }

    @Test
    void sendMessageAsync_Failure() {
        // Arrange
        SettableListenableFuture<SendResult<String, String>> listenableFuture = new SettableListenableFuture<>();
        listenableFuture.setException(new RuntimeException("Async error"));

        Mockito.<CompletableFuture<SendResult<String, String>>>when(kafkaTemplate.send(anyString(), anyString())).thenReturn(listenableFuture);

        // Act
        CompletableFuture<String> result = producerService.sendMessageAsync(testMessage);

        // Assert
        assertThrows(ExecutionException.class, () -> result.get());
    }

    @Test
    void sendMessageWithCallback_Success() {
        // Arrange
        RecordMetadata metadata = new RecordMetadata(new TopicPartition("meu-topico", 0), 0L, 0L, 0L, 0L, 0, 0);
        SendResult<String, String> sendResult = new SendResult<>(null, metadata);
        CompletableFuture<SendResult<String, String>> future = CompletableFuture.completedFuture(sendResult);
        when(kafkaTemplate.send(anyString(), anyString())).thenReturn(future);

        // Act & Assert
        assertDoesNotThrow(() -> {
            producerService.sendMessageWithCallback(testMessage, (result, ex) -> {
                assertNotNull(result);
                assertNull(ex);
            });
        });
    }

    @Test
    void sendMessageWithCallback_Failure() {
        // Arrange
        CompletableFuture<SendResult<String, String>> future = new CompletableFuture<>();
        future.completeExceptionally(new RuntimeException("Callback error"));
        when(kafkaTemplate.send(anyString(), anyString())).thenReturn(future);

        // Act & Assert
        assertDoesNotThrow(() -> {
            producerService.sendMessageWithCallback(testMessage, (result, ex) -> {
                assertNull(result);
                assertNotNull(ex);
            });
        });
    }
}