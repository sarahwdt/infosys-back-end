package fa.fitbda.infosys.controllers;

import fa.fitbda.infosys.dto.InstructorDTO;
import fa.fitbda.infosys.entities.Instructor;
import fa.fitbda.infosys.controllers.abstraction.BaseCRUDController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/instructor")
@RequiredArgsConstructor
public class InstructorRestController extends BaseCRUDController<Instructor, InstructorDTO> {
}
