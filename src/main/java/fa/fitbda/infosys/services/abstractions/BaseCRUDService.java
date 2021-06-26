package fa.fitbda.infosys.services.abstractions;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public abstract class BaseCRUDService<Entity extends AbstractPersistable<Long>> extends BaseROService<Entity> {
    public Entity create(Entity entity) {
        return baseRepository.save(entity);
    }

    public Entity update(Entity entity) {
        return baseRepository.save(entity);
    }

    public boolean delete(Entity entity) {
        baseRepository.delete(entity);
        return baseRepository.existsById(Objects.requireNonNull(entity.getId()));
    }

    public boolean delete(Long id) {
        baseRepository.deleteById(id);
        return baseRepository.existsById(id);
    }
}
