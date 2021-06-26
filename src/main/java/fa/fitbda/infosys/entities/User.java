package fa.fitbda.infosys.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity implements UserDetails {
    @Column(nullable = false, unique = true)
    @Size(min = 5, message = "Логин должен содержать не менее 5 символов")
    private String login;
    @Column(nullable = false)
    @Size(min = 5, message = "Пароль должен содержать не менее 5 символов")
    private String password;
    @Transient
    private String passwordConfirm;
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<Role> roles;

    public User(@Size(min = 5, message = "Логин должен содержать не менее 5 символов") String login,
                @Size(min = 5, message = "Пароль должен содержать не менее 5 символов") String password,
                String passwordConfirm) throws IllegalAccessException {
        this.login = login;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        if (!password.equals(passwordConfirm))
            throw new IllegalAccessException("Пароль и подтверждение пароля не совподают");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().flatMap(role -> role.getPrivileges().stream()).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
