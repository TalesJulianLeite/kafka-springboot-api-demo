package leite.tales.kafka.services.producers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

@Service
public class KafkaProducerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducerService.class);

    @Value("${kafka.topic.name:meu-topico}")
    private String topicName;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // Existing synchronous method
    public String sendMessage(String message) {
        log.info("Sending message to topic '{}': {}", topicName, message);
        try {
            kafkaTemplate.send(topicName, message).get();
            log.info("Message sent successfully");
            return message; // Retorna a mensagem confirmada
        } catch (Exception e) {
            log.error("Failed to send message", e);
            throw new RuntimeException("Failed to send message", e);
        }
    }

    // New asynchronous method returning CompletableFuture
    public CompletableFuture<String> sendMessageAsync(String message) {
        log.info("Asynchronously sending message to topic '{}': {}", topicName, message);

        CompletableFuture<String> future = new CompletableFuture<>();

        kafkaTemplate.send(topicName, message).whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Failed to send message asynchronously", ex);
                future.completeExceptionally(ex);
            } else {
                log.info("Message sent successfully to partition {} with offset {}",
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
                future.complete(String.valueOf(result));
            }
        });

        return future;
    }

    // Enhanced async method with callback
    public CompletableFuture<Void> sendMessageWithCallback(String message,
                                                           BiConsumer<SendResult<String, String>, Throwable> callback) {
        log.info("Sending message with custom callback to topic '{}'", topicName);

        kafkaTemplate.send(topicName, message).whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Failed to send message with callback", ex);
            } else {
                log.info("Message with callback sent successfully");
            }
            callback.accept(result, ex);
        });
        return null;
    }
}