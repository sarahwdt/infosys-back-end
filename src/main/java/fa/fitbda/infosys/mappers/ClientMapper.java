package fa.fitbda.infosys.mappers;

import fa.fitbda.infosys.dto.ClientDTO;
import fa.fitbda.infosys.entities.Client;
import fa.fitbda.infosys.mappers.abstractions.AbstractMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper extends AbstractMapper<Client, ClientDTO> {
    @Autowired
    public ClientMapper() {
        super(Client.class, ClientDTO.class);
    }
}
