package leite.tales.kafka.domain.interfaces;

public interface IdentifiableEntity<T> {
    T getId();
    void setId(T id);
}