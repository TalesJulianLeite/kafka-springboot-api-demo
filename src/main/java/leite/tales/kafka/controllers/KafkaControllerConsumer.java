package leite.tales.kafka.controllers;

import leite.tales.kafka.services.consumers.KafkaConsumerService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Setter
@RestController
@RequestMapping("/kafka")
public class KafkaControllerConsumer {

    @Autowired
    private KafkaConsumerService listener;

    @GetMapping("/listener")
    @ResponseBody
    public ResponseEntity<String> read() {
        return ResponseEntity.ok(listener.consumirMensagem());
    }

    @GetMapping("/listener/offset/{id}")
    @ResponseBody
    public ResponseEntity<String> readOffset(@PathVariable Long id) {
        return ResponseEntity.ok(listener.consumirMensagem());
    }


}
