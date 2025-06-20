package leite.tales.kafka.service.producers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void enviarMensagem(String mensagem) {
        kafkaTemplate.send("meu-topico", mensagem);
        System.out.println("Mensagem enviada: " + mensagem);
    }
}
