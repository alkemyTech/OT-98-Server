package com.alkemy.ong.config.seeder;

import com.alkemy.ong.config.ApplicationRole;
import com.alkemy.ong.model.entity.Role;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.repository.IRoleRepository;
import com.alkemy.ong.repository.IUserRepository;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsersSeeder implements CommandLineRunner {

  private static final String PHOTO = "https://foo.jpg";
  private static final long ROLE_USER = 1L;
  private static final long ROLE_ADMIN = 2L;
  private static final String PASSWORD_GENERIC = "foo12345";

  @Autowired
  IUserRepository userRepository;

  @Autowired
  IRoleRepository roleRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public void run(String... args) throws Exception {
    loadRoles();
    loadUsers();
  }

  private void loadRoles() {
    if (roleRepository.count() == 0) {
      roleRepository.save(buildRole(ApplicationRole.USER));
      roleRepository.save(buildRole(ApplicationRole.ADMIN));
    }
  }

  private Role buildRole(ApplicationRole applicationRole) {
    Role role = new Role();
    role.setId(getRoleId(applicationRole));
    role.setName(applicationRole.getFullRoleName());
    return role;
  }

  private long getRoleId(ApplicationRole applicationRole) {
    return applicationRole == ApplicationRole.USER ? ROLE_USER : ROLE_ADMIN;
  }

  private void loadUsers() {
    if (userRepository.count() == 0) {
      loadUsersWithRoleUser();
      loadUsersWithRoleAdmin();
    }
  }

  private void loadUsersWithRoleUser() {
    userRepository.save(buildUser(1L, "Ignacio", "Montovio", "imontovio@alkemy.com"));
    userRepository.save(buildUser(2L, "Alexis", "Bahi", "abahi@alkemy.com"));
    userRepository.save(buildUser(3L, "Joaquin", "Aman", "jaman@alkemy.com"));
    userRepository.save(buildUser(4L, "Kevin", "Lugo", "klugo@alkemy.com"));
    userRepository.save(buildUser(5L, "Lucio", "Scaceres", "lscaceres@alkemy.com"));
    userRepository.save(buildUser(6L, "Matias", "Cevini", "mcevini@alkemy.com"));
    userRepository.save(buildUser(7L, "Oscar", "Ruina", "oruina@alkemy.com"));
    userRepository.save(buildUser(8L, "Magali", "Kain", "mkain@alkemy.com"));
    userRepository.save(buildUser(9L, "Pablo", "Samid", "psamid@alkemy.com"));
    userRepository.save(buildUser(10L, "Alejandro", "Ruiz", "aruiz@alkemy.com"));
  }

  private void loadUsersWithRoleAdmin() {
    userRepository.save(buildUserAdmin(11L, "Marip", "Ruiz", "mruiz@alkemy.com"));
    userRepository.save(buildUserAdmin(12L, "Lucas", "Lopez", "llopez@alkemy.com"));
    userRepository.save(buildUserAdmin(13L, "Santiago", "Tierno", "stierno@alkemy.com"));
    userRepository.save(buildUserAdmin(14L, "Julieta", "Tierno", "jtierno@alkemy.com"));
    userRepository.save(buildUserAdmin(15L, "Daniela", "Tierno", "dtierno@alkemy.com"));
    userRepository.save(buildUserAdmin(16L, "Naiara", "Paez", "npaez@alkemy.com"));
    userRepository.save(buildUserAdmin(17L, "Julian", "Paez", "jpaez@alkemy.com"));
    userRepository.save(buildUserAdmin(18L, "Josias", "Santoro", "jsantoro@alkemy.com"));
    userRepository.save(buildUserAdmin(19L, "Federico", "Santoro", "fesantoro@alkemy.com"));
    userRepository.save(buildUserAdmin(20L, "Ezequiel", "Santoro", "esantoro@alkemy.com"));
  }

  private User buildUser(long id, String firstName, String lastName, String email) {
    return new User(id,
        firstName,
        lastName,
        email,
        bCryptPasswordEncoder.encode(PASSWORD_GENERIC),
        PHOTO,
        List.of(roleRepository.findById(ROLE_USER).get()),
        Timestamp.from(Instant.now()),
        false);
  }

  private User buildUserAdmin(long id, String firstName, String lastName, String email) {
    return new User(id,
        firstName,
        lastName,
        email,
        bCryptPasswordEncoder.encode(PASSWORD_GENERIC),
        PHOTO,
        List.of(roleRepository.findById(ROLE_ADMIN).get()),
        Timestamp.from(Instant.now()),
        false);
  }

}