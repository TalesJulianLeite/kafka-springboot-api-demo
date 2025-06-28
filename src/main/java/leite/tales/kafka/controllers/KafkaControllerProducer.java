package leite.tales.kafka.controllers;

import leite.tales.kafka.services.producers.KafkaProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/kafka")
public class KafkaControllerProducer {

    private KafkaProducerService producerService;

    public KafkaControllerProducer(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody String message) {
        producerService.sendMessage(message);
        return ResponseEntity.ok("Message sent to Kafka topic successfully");
    }

    @PostMapping("/send-async")
    public CompletableFuture<Void> sendMessageAsync(@RequestBody String message) {
        return producerService.sendMessageAsync("my message")
                .thenAccept(result -> {
                    System.out.println("Sent to offset: " + result.getRecordMetadata().offset());
                })
                .exceptionally(ex -> {
                    System.err.println("Failed: " + ex.getMessage());
                    return null;
                });
    }
}
