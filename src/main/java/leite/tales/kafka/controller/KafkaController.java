
package leite.tales.kafka.controller;

import leite.tales.kafka.service.producers.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    private KafkaProducerService producer;

    @PostMapping("/enviar")
    public ResponseEntity<String> enviar(@RequestBody String mensagem) {
        producer.enviarMensagem(mensagem);
        return ResponseEntity.ok("Mensagem enviada!");
    }
}
