package leite.tales.kafka.util.factory.interfaces;

public interface MessageHandler<T> {
    void handle(T payload);
}
