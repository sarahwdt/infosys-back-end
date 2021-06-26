package fa.fitbda.infosys.util;

import fa.fitbda.infosys.entities.Privilege;
import fa.fitbda.infosys.entities.Role;
import fa.fitbda.infosys.entities.User;
import fa.fitbda.infosys.repositories.PrivilegeRepository;
import fa.fitbda.infosys.repositories.RoleRepository;
import fa.fitbda.infosys.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;

        Privilege readHistory = createPrivilegeIfNotFound("READ_HISTORY");
        Privilege readClient = createPrivilegeIfNotFound("READ_CLIENT");
        Privilege readSession = createPrivilegeIfNotFound("READ_SESSION");
        Privilege readInstructor = createPrivilegeIfNotFound("READ_INSTRUCTOR");
        Privilege readUser = createPrivilegeIfNotFound("READ_USER");
        Privilege readRole = createPrivilegeIfNotFound("READ_ROLE");
        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writeClient = createPrivilegeIfNotFound("WRITE_CLIENT");
        Privilege writeSession = createPrivilegeIfNotFound("WRITE_SESSION");
        Privilege writeInstructor = createPrivilegeIfNotFound("WRITE_INSTRUCTOR");
        Privilege writeUser = createPrivilegeIfNotFound("WRITE_USER");
        Privilege writeRole = createPrivilegeIfNotFound("WRITE_ROLE");
        Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        List<Privilege> clientPrivileges = Arrays.asList(readInstructor, readSession);
        List<Privilege> instructorPrivileges = Arrays.asList(readClient, readInstructor, readSession, writeSession,
                writeClient);
        List<Privilege> managerPrivileges = Arrays.asList(readClient, readInstructor, readSession, writeClient,
                writeSession, writeInstructor);
        List<Privilege> adminPrivileges = Arrays.asList(readClient, readInstructor, readSession, readUser, readRole,
                readHistory, readPrivilege, writeUser, writeRole, writePrivilege);
        List<Privilege> rootPrivileges = Arrays.asList(readClient, readInstructor, readSession, readUser, readRole,
                readPrivilege, writeUser, writeRole, writePrivilege, writeClient, writeInstructor, writeSession);

        createRoleIfNotFound("ROLE_CLIENT", clientPrivileges);
        createRoleIfNotFound("ROLE_INSTRUCTOR", instructorPrivileges);
        createRoleIfNotFound("ROLE_MANAGER", managerPrivileges);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_ROOT", rootPrivileges);

        Role rootRole = roleRepository.findByName("ROLE_ROOT").orElseThrow();
        userRepository.findByLogin("ROOT_USER").ifPresentOrElse(
                user -> user.setRoles(Collections.singletonList(rootRole)),
                () -> {
                    User user = new User();
                    user.setLogin("ROOT_USER");
                    user.setPassword(passwordEncoder.encode("slaves"));
                    user.setRoles(Collections.singletonList(rootRole));
                    userRepository.save(user);
                });
        alreadySetup = true;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {
        return privilegeRepository.findByName(name)
                .orElseGet(() -> privilegeRepository.save(new Privilege(name)));
    }

    @Transactional
    Role createRoleIfNotFound(String name, List<Privilege> privileges) {
        return roleRepository.findByName(name)
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(name);
                    newRole.setPrivileges(privileges);
                    return roleRepository.save(newRole);
                });
    }
}