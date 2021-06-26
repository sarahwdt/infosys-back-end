package fa.fitbda.infosys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseDTO {
    private Long id;
    private UserDTO createdBy;
    private UserDTO lastModifiedBy;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
}
