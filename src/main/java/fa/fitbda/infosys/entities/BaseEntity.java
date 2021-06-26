package fa.fitbda.infosys.entities;

import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity extends AbstractAuditable<User, Long> {
    @Override
    public void setId(Long id) {
        super.setId(id);
    }
}
