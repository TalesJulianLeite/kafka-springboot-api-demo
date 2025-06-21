package leite.tales.kafka.controllers;

import leite.tales.kafka.services.consumers.KafkaConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
public class KafkaControllerConsumer {

    @Autowired
    private KafkaConsumerService listener;

    @GetMapping("/listener")
    public ResponseEntity<String> read() {
        return ResponseEntity.ok(listener.consumirMensagem());
    }
}
