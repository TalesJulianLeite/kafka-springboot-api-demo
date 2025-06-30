package leite.tales.kafka.services.consumers;

import leite.tales.kafka.exceptions.KafkaConsumerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);
    private static final String TOPIC = "meu-topico";
    private static final String GROUP = "meu-grupo";
    private String lastMessage = null;


    @KafkaListener(topics = "${kafka.topic.name:meu-topico}", groupId = "${kafka.consumer.group:meu-grupo}")
    public String consumirMensagem() throws KafkaConsumerException {
        log.info("Consuming message from topic: {}, group: {}, message: {}", TOPIC, GROUP, lastMessage);
        if (lastMessage == null) {
            throw new KafkaConsumerException("Message content is null");
        }
        return lastMessage;
    }

    public String getLastConsumedMessage() {
        if (lastMessage == null) {
            throw new KafkaConsumerException("No messages consumed yet");
        }
        return lastMessage;
    }
}
