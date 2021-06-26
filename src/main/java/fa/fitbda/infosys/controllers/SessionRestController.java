package fa.fitbda.infosys.controllers;

import fa.fitbda.infosys.dto.SessionDTO;
import fa.fitbda.infosys.entities.Session;
import fa.fitbda.infosys.controllers.abstraction.BaseCRUDController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/session")
@RequiredArgsConstructor
public class SessionRestController extends BaseCRUDController<Session, SessionDTO> {
}
