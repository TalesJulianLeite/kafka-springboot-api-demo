package leite.tales.kafka.controllers;

import leite.tales.kafka.services.GenericRestTemplateService;
import leite.tales.kafka.services.producers.KafkaProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/kafka")
public class KafkaProducerController {

    private KafkaProducerService producerService;
    private GenericRestTemplateService restService;

    public KafkaProducerController(KafkaProducerService producerService, GenericRestTemplateService restService) {
        this.producerService = producerService;
        this.restService = restService;
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestBody String message) {
        producerService.sendMessage(message);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/externo")
    public ResponseEntity<String> chamaServicoExterno() {
        String url = "https://httpbin.org/get"; // exemplo
        return restService.get(url, String.class);
    }

    @PostMapping("/externo-post")
    public ResponseEntity<String> chamaPostExterno(@RequestBody Map<String, Object> payload) {
        String url = "https://httpbin.org/post"; // exemplo
        return restService.post(url, payload, String.class);
    }

}
