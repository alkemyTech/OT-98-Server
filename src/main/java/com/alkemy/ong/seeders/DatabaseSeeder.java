package com.alkemy.ong.seeders;

import com.alkemy.ong.config.ApplicationRole;
import com.alkemy.ong.model.entity.Role;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.repository.IRoleRepository;
import com.alkemy.ong.repository.IUserRepository;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

  Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);

  @Autowired
  private IUserRepository userRepository;

  @Autowired
  private IRoleRepository roleRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public void run(String... args) throws Exception {
    seedRolesTable();
    seedUsersTable();
  }

  private void seedRolesTable() {
    if (roleRepository.count() == 0) {
      roleRepository.save(buildRole("ROLE_USER"));
      roleRepository.save(buildRole("ROLE_ADMIN"));
      logger.info("Roles table seeded.");
    } else {
      logger.trace("Roles Seeding Not Required.");
    }
  }

  private Role buildRole(String name) {
    Role role = new Role();
    if (name == "ROLE_ADMIN") {
      role.setName(ApplicationRole.ADMIN.getFullRoleName());
      role.setDescription(ApplicationRole.ADMIN.getName());
      return role;
    }
    if (name == "ROLE_USER") {
      role.setName(ApplicationRole.USER.getFullRoleName());
      role.setDescription(ApplicationRole.USER.getName());
      return role;
    }
    return null;
  }

  private User buildUser(String firstName, String lastName, String email, String password,
      String photo, List<Role> roles) {
    User user = new User();
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setEmail(email);
    user.setPassword(bCryptPasswordEncoder.encode(password));
    user.setPhoto(
        "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
    user.setRoles(roles);
    return user;
  }

  private void seedUsersTable() {

    if (userRepository.count() == 0) {
      Role roleAdmin = roleRepository.findByName(ApplicationRole.ADMIN.getFullRoleName());
      Role roleUser = roleRepository.findByName(ApplicationRole.USER.getFullRoleName());
      List<Role> rolesAdmin = new ArrayList<Role>();
      rolesAdmin.add(roleAdmin);
      List<Role> rolesUser = new ArrayList<Role>();
      rolesUser.add(roleUser);

      //------------------ Admin -------------------------
      User userIgnacio = buildUser("Ignacio", "Montovio",
          "imontovio@alkemy.com",
          "imontovio",
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png",
          rolesAdmin);

      User userAlexis = buildUser("Alexis", "Bahi",
          "abahi@alkemy.com",
          "abahi",
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png",
          rolesAdmin);

      User userJoaquin = buildUser("Joaquin", "Aman",
          "jaman@alkemy.com",
          "jaman",
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png",
          rolesAdmin);

      User userKevin = buildUser("Kevin", "Lugo",
          "klugo@alkemy.com",
          "klugo",
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png",
          rolesAdmin);

      User userLucio = buildUser("Lucio", "Scaceres",
          "lscaceres@alkemy.com",
          "lscaceres",
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png",
          rolesAdmin);

      User userMatias = buildUser("Matias", "Cevini",
          "mcevini@alkemy.com",
          "mcevini",
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png",
          rolesAdmin);

      User userOscar = buildUser("Oscar", "Ruina",
          "oruina@alkemy.com",
          "oruina",
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png",
          rolesAdmin);

      User userMagali = buildUser("Magali", "Kain",
          "mkain@alkemy.com",
          "mkain",
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png",
          rolesAdmin);

      User userPablo = buildUser("Pablo", "Samid",
          "psamid@alkemy.com",
          "psamid",
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png",
          rolesAdmin);

      User userAlejandro = buildUser("Alejandro", "Ruiz",
          "aruiz@alkemy.com",
          "aruiz",
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png",
          rolesAdmin);

      //------------------ User -------------------------

      User userMario = buildUser("Mario", "Ruiz",
          "mruiz@alkemy.com",
          "mruiz",
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png",
          rolesUser);

      User userLucas = buildUser("Lucas", "Lopez",
          "llopez@alkemy.com",
          "llopez",
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png",
          rolesUser);

      User userSantiago = buildUser("Santiago", "Tierno",
          "stierno@alkemy.com",
          "stierno",
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png",
          rolesUser);

      User userJulieta = buildUser("Julieta", "Tierno",
          "jtierno@alkemy.com",
          "jtierno",
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png",
          rolesUser);

      User userDaniela = buildUser("Daniela", "Tierno",
          "dtierno@alkemy.com",
          "dtierno",
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png",
          rolesUser);

      User userNaiara = buildUser("Naiara", "Paez",
          "npaez@alkemy.com",
          "npaez",
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png",
          rolesUser);

      User userJulian = buildUser("Julian", "Paez",
          "jpaez@alkemy.com",
          "jpaez",
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png",
          rolesUser);

      User userJosias = buildUser("Josias", "Santoro",
          "jsantoro@alkemy.com",
          "jsantoro",
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png",
          rolesUser);

      User userFederico = buildUser("Federico", "Santoro",
          "fsantoro@alkemy.com",
          "fsantoro",
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png",
          rolesUser);

      User userEzequiel = buildUser("Ezequiel", "Santoro",
          "esantoro@alkemy.com",
          "esantoro",
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png",
          rolesUser);

      userRepository.saveAll(new ArrayList<User>() {{
        add(userIgnacio);
        add(userAlexis);
        add(userJoaquin);
        add(userKevin);
        add(userLucio);
        add(userMatias);
        add(userOscar);
        add(userMagali);
        add(userPablo);
        add(userAlejandro);
        add(userMario);
        add(userLucas);
        add(userSantiago);
        add(userJulieta);
        add(userDaniela);
        add(userNaiara);
        add(userJulian);
        add(userJosias);
        add(userFederico);
        add(userEzequiel);
      }});
      logger.info("User table seeded");
    } else {
      logger.trace("User Seeding Not Required.");
    }
  }

}
