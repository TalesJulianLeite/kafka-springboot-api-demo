package leite.tales.kafka.services.interfaces;

import leite.tales.kafka.domain.interfaces.IdentifiableEntity;

import java.util.Optional;

public interface GenericMessageService<T extends IdentifiableEntity<ID>, ID>> implements GenericMessageService<T, ID> {
    T readMessage(T entity);
    T readMessageByOffset(String topic, String group, T entity, Long offset);
    void writeMessage(T entity);
    T readMessageByOffset(T entity);
    T writeMessage(String topic, String group, T entity, Long offset);
}
