package fa.fitbda.infosys.controllers;

import fa.fitbda.infosys.dto.ClientDTO;
import fa.fitbda.infosys.entities.Client;
import fa.fitbda.infosys.controllers.abstraction.BaseCRUDController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientRestController extends BaseCRUDController<Client, ClientDTO> {
}
