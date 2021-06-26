package fa.fitbda.infosys.repositories;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseCRUDRepository<Entity extends AbstractPersistable<Long>>
        extends JpaRepository<Entity, Long> {
}
