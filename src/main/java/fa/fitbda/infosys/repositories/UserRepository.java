package fa.fitbda.infosys.repositories;

import fa.fitbda.infosys.entities.User;

import java.util.Optional;

public interface UserRepository extends BaseCRUDRepository<User> {
    Optional<User> findByLogin(String login);
}
