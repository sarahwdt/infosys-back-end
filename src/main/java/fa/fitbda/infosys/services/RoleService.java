package fa.fitbda.infosys.services;

import fa.fitbda.infosys.entities.Role;
import fa.fitbda.infosys.repositories.RoleRepository;
import fa.fitbda.infosys.services.abstractions.BaseCRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService extends BaseCRUDService<Role> {
    private final RoleRepository roleRepository;
}
