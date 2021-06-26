package fa.fitbda.infosys.mappers;

import fa.fitbda.infosys.dto.InstructorDTO;
import fa.fitbda.infosys.entities.Instructor;
import fa.fitbda.infosys.mappers.abstractions.AbstractMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InstructorMapper extends AbstractMapper<Instructor, InstructorDTO> {
    @Autowired
    public InstructorMapper() {
        super(Instructor.class, InstructorDTO.class);
    }
}
