package fa.fitbda.infosys.services;

import fa.fitbda.infosys.entities.Privilege;
import fa.fitbda.infosys.repositories.PrivilegeRepository;
import fa.fitbda.infosys.services.abstractions.BaseCRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrivilegeService extends BaseCRUDService<Privilege> {
    protected final PrivilegeRepository privilegeRepository;
}
