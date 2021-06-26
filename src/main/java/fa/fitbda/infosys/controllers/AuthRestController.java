package fa.fitbda.infosys.controllers;

import fa.fitbda.infosys.dto.UserDTO;
import fa.fitbda.infosys.entities.User;
import fa.fitbda.infosys.mappers.abstractions.Mapper;
import fa.fitbda.infosys.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthRestController {
    private final Mapper<User, UserDTO> baseMapper;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<UserDTO> getAuth() {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof User) {
                return ResponseEntity.ok(baseMapper.toDto((User) principal));
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            return ResponseEntity.noContent().build();

        }

    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody Map<String, String> json) {
        return userService.findByLogin(json.get("username")).map(user ->
                passwordEncoder.matches(json.get("password"), user.getPassword()) ? ResponseEntity.ok().build()
                        : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Пароли не совпадают")))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Пользователь с таким именем не найден")));
    }

    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public ResponseEntity<?> reg(@RequestBody Map<String, String> json) {
        try {
            if (userService.findByLogin(json.get("login")).isPresent())
                return ResponseEntity.badRequest().body(Map.of("error", "Пользователь с таки именем существует"));
            User newUser = new User(json.get("login"), json.get("password"), json.get("passwordConfirm"));
            userService.create(newUser);
            return ResponseEntity.ok().build();
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getCause());
        }
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<?> changeSelf(@RequestBody Map<String, String> json) {
        String login = json.get("login");
        String password = json.get("password");
        String passwordConfirm = json.get("passwordConfirm");
        if ((login != null && !login.isEmpty()) && login.length() < 5)
            ResponseEntity.badRequest().body(Map.of("error", "Логин должен содержать больше 5 символов"));
        if ((password != null && !password.isEmpty()) && password.length() < 5)
            ResponseEntity.badRequest().body(Map.of("error", "Пароль должен содержать больше 5 символов"));
        if ((password != null && !password.isEmpty()) && !password.equals(passwordConfirm))
            ResponseEntity.badRequest().body(Map.of("error", "Пароль и подтверждение пароля не совпадают"));
        String currentUser = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getLogin();
        return userService.findByLogin(currentUser).map(user -> {
            if (login != null && !login.isEmpty()) user.setLogin(login);
            if (password != null && !password.isEmpty()) user.setPassword(password);
            return userService.update(user);
        }).map(user -> ResponseEntity.ok().build())
                .orElse(ResponseEntity.badRequest().body(Map.of("error", "Отсутсвуют данные")));
    }
}
