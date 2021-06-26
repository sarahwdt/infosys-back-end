package fa.fitbda.infosys.services;

import fa.fitbda.infosys.entities.Instructor;
import fa.fitbda.infosys.entities.Session;
import fa.fitbda.infosys.repositories.InstructorRepository;
import fa.fitbda.infosys.repositories.SessionRepository;
import fa.fitbda.infosys.services.abstractions.BaseCRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstructorService extends BaseCRUDService<Instructor> {
    private final InstructorRepository instructorRepository;

}
