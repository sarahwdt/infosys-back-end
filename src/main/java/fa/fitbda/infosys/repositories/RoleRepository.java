package fa.fitbda.infosys.repositories;

import fa.fitbda.infosys.entities.Role;

import java.util.Optional;

public interface RoleRepository extends BaseCRUDRepository<Role> {
    Optional<Role> findByName(String name);
}
