package leite.tales.kafka.util.factory.impl;

import leite.tales.kafka.util.factory.interfaces.MessageHandler;
import org.springframework.kafka.annotation.KafkaListener;

public class GenericKafkaConsumer<T> {

    private final MessageHandler<T> handler;

    public GenericKafkaConsumer(MessageHandler<T> handler) {
        this.handler = handler;
    }

    @KafkaListener(topics = "${kafka.topic}")
    public void consume(T payload) {
        handler.handle(payload);
    }

}