package fa.fitbda.infosys.mappers;

import fa.fitbda.infosys.dto.RoleDTO;
import fa.fitbda.infosys.entities.Role;
import fa.fitbda.infosys.mappers.abstractions.AbstractMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper extends AbstractMapper<Role, RoleDTO> {
    @Autowired
    public RoleMapper() {
        super(Role.class, RoleDTO.class);
    }
}
