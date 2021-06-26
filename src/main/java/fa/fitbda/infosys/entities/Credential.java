package fa.fitbda.infosys.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Credential extends BaseEntity {
    @Size(min = 2, message = "Имя не может быть короче двух символов")
    private String firstName;
    @Size(min = 2, message = "Фамилия не может быть короче двух символов")
    private String secondName;

    public String getFullName() {
        return secondName.concat(" ").concat(firstName);
    }

    @Transient
    private String fullName;
    private String middleName;
    @Size(min = 12, max = 12, message = "Формат номера телефона +7XXXXXXXXXX")
    private String phone;
    @Email(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$",
            message = "Невалидный e-mail")
    private String email;

    @Override
    public void setId(Long id) {
        super.setId(id);
    }
}
