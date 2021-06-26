package fa.fitbda.infosys.mappers;

import fa.fitbda.infosys.dto.UserDTO;
import fa.fitbda.infosys.entities.User;
import fa.fitbda.infosys.mappers.abstractions.AbstractMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends AbstractMapper<User, UserDTO> {
    @Autowired
    public UserMapper() {
        super(User.class, UserDTO.class);
    }
}
