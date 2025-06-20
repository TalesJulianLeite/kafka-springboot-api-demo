package leite.tales.kafka.service.consumers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerService {

    @KafkaListener(topics = "meu-topico", groupId = "meu-grupo")
    public void consumirMensagem(String mensagem) {
        System.out.println("Mensagem recebida: " + mensagem);
    }
}
