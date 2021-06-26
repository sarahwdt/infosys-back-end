package fa.fitbda.infosys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CredentialDTO extends BaseDTO{
    private String firstName;
    private String secondName;
    private String middleName;
    private String phone;
    private String email;
}
