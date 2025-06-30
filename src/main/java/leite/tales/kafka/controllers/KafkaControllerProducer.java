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

}
