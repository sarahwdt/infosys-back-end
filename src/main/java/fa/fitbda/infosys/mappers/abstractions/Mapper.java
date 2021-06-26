package fa.fitbda.infosys.mappers.abstractions;

import fa.fitbda.infosys.dto.BaseDTO;
import fa.fitbda.infosys.entities.BaseEntity;

public interface Mapper<E extends BaseEntity, D extends BaseDTO> {
    E toEntity(D dto);

    D toDto(E entity);
}