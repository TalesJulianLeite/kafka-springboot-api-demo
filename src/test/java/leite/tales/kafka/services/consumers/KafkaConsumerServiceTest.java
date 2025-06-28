package leite.tales.kafka.services.consumers;

import leite.tales.kafka.exceptions.KafkaConsumerException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class KafkaConsumerServiceTest {

    @InjectMocks
    private KafkaConsumerService consumerService;

    private final String testMessage = "test-message";

    // Método auxiliar para definir o lastMessage via reflection
    private void setLastMessage(String message) throws Exception {
        Field lastMessageField = KafkaConsumerService.class.getDeclaredField("lastMessage");
        lastMessageField.setAccessible(true);
        lastMessageField.set(consumerService, message);
    }

    @Test
    void consumirMensagem_Success() throws Exception {
        // Arrange
        setLastMessage(testMessage);

        // Act
        String result = consumerService.consumirMensagem();

        // Assert
        assertEquals(testMessage, result);
    }

    @Test
    void consumirMensagem_NullMessage_ThrowsException() throws Exception {
        // Arrange
        setLastMessage(null);

        // Act & Assert
        assertThrows(KafkaConsumerException.class, () -> consumerService.consumirMensagem());
    }

    @Test
    void getLastConsumedMessage_Success() throws Exception {
        // Arrange
        setLastMessage(testMessage);

        // Act
        String result = consumerService.getLastConsumedMessage();

        // Assert
        assertEquals(testMessage, result);
    }

    @Test
    void getLastConsumedMessage_NullMessage_ThrowsException() throws Exception {
        // Arrange
        setLastMessage(null);

        // Act & Assert
        assertThrows(KafkaConsumerException.class, () -> consumerService.getLastConsumedMessage());
    }
}