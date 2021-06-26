package fa.fitbda.infosys.controllers;

import fa.fitbda.infosys.dto.UserDTO;
import fa.fitbda.infosys.entities.User;
import fa.fitbda.infosys.controllers.abstraction.BaseCRUDController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserRestController extends BaseCRUDController<User, UserDTO> {

}
