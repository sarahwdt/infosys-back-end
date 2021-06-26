package fa.fitbda.infosys.controllers;

import fa.fitbda.infosys.dto.RoleDTO;
import fa.fitbda.infosys.entities.Role;
import fa.fitbda.infosys.controllers.abstraction.BaseCRUDController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleRestController extends BaseCRUDController<Role, RoleDTO> {
}
