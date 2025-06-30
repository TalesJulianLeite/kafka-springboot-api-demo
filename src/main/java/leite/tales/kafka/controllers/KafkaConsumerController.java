package leite.tales.kafka.controllers;

import leite.tales.kafka.services.GenericRestTemplateService;
import leite.tales.kafka.services.consumers.KafkaConsumerService;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Setter
@RestController
@RequestMapping("/kafka")
public class KafkaConsumerController {

    private KafkaConsumerService listener;
    private GenericRestTemplateService restService;

    public KafkaConsumerController(KafkaConsumerService listener, GenericRestTemplateService restService) {
        this.listener = listener;
        this.restService = restService;
    }

    @GetMapping("/listener")
    public ResponseEntity<String> read() {
        return ResponseEntity.ok(listener.consumirMensagem());
    }

    @GetMapping("/listener")
    public ResponseEntity<String> readLastMessage() {
        return ResponseEntity.ok(listener.getLastConsumedMessage());
    }

    @GetMapping("/listener/offset/{id}")
    public ResponseEntity<String> readOffset(@PathVariable Long id) {
        return ResponseEntity.ok(listener.);
    }

}
