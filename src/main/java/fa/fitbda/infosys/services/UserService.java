package fa.fitbda.infosys.services;

import fa.fitbda.infosys.entities.User;
import fa.fitbda.infosys.repositories.RoleRepository;
import fa.fitbda.infosys.repositories.UserRepository;
import fa.fitbda.infosys.services.abstractions.BaseCRUDService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService extends BaseCRUDService<User> implements UserDetailsService {
    protected final UserRepository userRepository;
    protected final RoleRepository roleRepository;
    protected final RoleService roleService;
    @Setter(onMethod_ = {@Autowired, @Lazy})
    protected PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.findByLogin(login).orElseThrow(() ->
                new UsernameNotFoundException("Пользователь с логином " + login + " не найден"));
    }

    @Override
    public User create(@NotNull User user) {
        if (userRepository.findByLogin(user.getLogin()).isPresent())
            return null;
        else {
            user.setRoles(Collections.singletonList(roleRepository.findByName("ROLE_CLIENT").orElse(null)));
            user.setPassword(encoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
    }

    public Optional<User> findByLogin(String login){
        return userRepository.findByLogin(login);
    }

}
