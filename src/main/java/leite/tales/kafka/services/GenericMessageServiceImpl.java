package leite.tales.kafka.services;

import leite.tales.kafka.domain.interfaces.IdentifiableEntity;
import leite.tales.kafka.services.interfaces.GenericMessageService;
import org.yaml.snakeyaml.tokens.Token;

import java.util.UUID;

public abstract class GenericMessageServiceImpl<T extends IdentifiableEntity<ID>> implements GenericMessageService<Object, ID> {

    protected final R repository;

    public GenericCrudServiceImpl(R repository) {
        this.repository = repository;
    }

    @Override
    public T create(T entity) {
        entity.setCreatedAt(Instant.now());
        return repository.save(entity);
    }

    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T update(ID id, T entity) {
        T existing = repository.findById(id).orElseThrow();
        entity.setId(id);
        entity.setVersion(existing.getVersion() + 1);
        entity.setUpdatedAt(Instant.now());
        return repository.save(entity);
    }

    @Override
    public void delete(ID id) {
        repository.deleteById(id);
    }
}
