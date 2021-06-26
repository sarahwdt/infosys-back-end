package fa.fitbda.infosys.repositories;

import fa.fitbda.infosys.entities.Privilege;

import java.util.Optional;

public interface PrivilegeRepository extends BaseCRUDRepository<Privilege> {
    Optional<Privilege> findByName(String name);
}
