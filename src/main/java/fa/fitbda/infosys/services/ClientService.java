package fa.fitbda.infosys.services;

import fa.fitbda.infosys.entities.Client;
import fa.fitbda.infosys.entities.Session;
import fa.fitbda.infosys.repositories.ClientRepository;
import fa.fitbda.infosys.repositories.SessionRepository;
import fa.fitbda.infosys.services.abstractions.BaseCRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService extends BaseCRUDService<Client> {
    private final ClientRepository clientRepository;

}
