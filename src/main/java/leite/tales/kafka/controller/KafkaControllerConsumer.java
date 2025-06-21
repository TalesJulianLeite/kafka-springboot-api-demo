package leite.tales.kafka.controller;

import leite.tales.kafka.service.consumers.KafkaConsumerService;
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
