package fa.fitbda.infosys.mappers;

import fa.fitbda.infosys.dto.SessionDTO;
import fa.fitbda.infosys.entities.Session;
import fa.fitbda.infosys.mappers.abstractions.AbstractMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionMapper extends AbstractMapper<Session, SessionDTO> {
    @Autowired
    public SessionMapper() {
        super(Session.class, SessionDTO.class);
    }
}
