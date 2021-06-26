package fa.fitbda.infosys.mappers;

import fa.fitbda.infosys.dto.PrivilegeDTO;
import fa.fitbda.infosys.entities.Privilege;
import fa.fitbda.infosys.mappers.abstractions.AbstractMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PrivilegeMapper extends AbstractMapper<Privilege, PrivilegeDTO> {
    @Autowired
    public PrivilegeMapper() {
        super(Privilege.class, PrivilegeDTO.class);
    }
}
