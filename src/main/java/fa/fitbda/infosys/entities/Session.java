package fa.fitbda.infosys.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Session extends BaseEntity {
    @ManyToOne(cascade=CascadeType.MERGE )
    private Instructor instructor;
    @Transient
    private String instructorName;

    public String getInstructorName() {
        return instructor == null? "" : instructor.getFullName();
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime date;
    @ManyToMany(cascade=CascadeType.MERGE )
    private List<Client> clients;
    private String comment;
}
