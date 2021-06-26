package fa.fitbda.infosys.services.abstractions;

import fa.fitbda.infosys.repositories.BaseCRUDRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public abstract class BaseROService<Entity extends AbstractPersistable<Long>> {

    @Setter(onMethod_ = {@Autowired})
    protected BaseCRUDRepository<Entity> baseRepository;

    public Optional<Entity> get(Long id) {
        return baseRepository.findById(id);
    }

    public Page<Entity> getAll(Pageable pageable) {
        return baseRepository.findAll(pageable);
    }

    public Page<Entity> getAll() {
        return baseRepository.findAll(PageRequest.of(0, (int) baseRepository.count()));
    }
}
