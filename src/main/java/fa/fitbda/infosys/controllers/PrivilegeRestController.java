package fa.fitbda.infosys.controllers;

import fa.fitbda.infosys.dto.PrivilegeDTO;
import fa.fitbda.infosys.entities.Privilege;
import fa.fitbda.infosys.controllers.abstraction.BaseCRUDController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/privilege")
@RequiredArgsConstructor
public class PrivilegeRestController extends BaseCRUDController<Privilege, PrivilegeDTO> {
}
