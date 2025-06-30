package leite.tales.kafka.domain;

import leite.tales.kafka.domain.interfaces.IdentifiableEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public abstract class GenericEntity<T> implements IdentifiableEntity<T> {
    protected T id;
    protected Long version;
    protected Instant createdAt;
    protected Instant updatedAt;
}
