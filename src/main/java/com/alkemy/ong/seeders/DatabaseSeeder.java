package com.alkemy.ong.seeders;

import com.alkemy.ong.model.entity.Role;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.repository.IRoleRepository;
import com.alkemy.ong.repository.IUserRepository;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder {

  @Autowired
  private IUserRepository userRepository;

  @Autowired
  private IRoleRepository roleRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @EventListener
  public void seed(ContextRefreshedEvent event) {
    seedRolesTable();
    seedUsersTable();
  }

  @Transactional
  private void seedRolesTable() {
    String sql = "SELECT description FROM roles R WHERE R.description = \"ROLE_USER\" OR " +
        "R.description = \"ROLE_ADMIN\" LIMIT 1";
    List<Role> u = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
    if (u == null || u.size() <= 0) {

      Role roleUser = new Role();
      roleUser.setName("USER");
      roleUser.setDescription("ROLE_USER");

      Role roleAdmin = new Role();
      roleAdmin.setName("ADMIN");
      roleAdmin.setDescription("ROLE_ADMIN");

      roleRepository.save(roleUser);
      roleRepository.save(roleAdmin);
    } else {
      System.out.println("Users Seeding Not Required");
    }
  }

  @Transactional
  private void seedUsersTable() {
    String sql =
        "SELECT email FROM users U WHERE U.email = \"imontovio@alkemy.com\"  LIMIT 1";
    List<User> u = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);

    if (u == null || u.size() <= 0) {
      Role roleAdmin = roleRepository.findByName("ADMIN");
      Role roleUser = roleRepository.findByName("USER");
      List<Role> rolesAdmin = new ArrayList<Role>();
      rolesAdmin.add(roleAdmin);
      List<Role> rolesUser = new ArrayList<Role>();
      rolesUser.add(roleUser);

      //------------------ Admin -------------------------
      User userIgnacio = new User();
      userIgnacio.setFirstName("Ignacio");
      userIgnacio.setLastName("Montovio");
      userIgnacio.setEmail("imontovio@alkemy.com");
      userIgnacio.setPassword(new BCryptPasswordEncoder().encode("imontovio"));
      userIgnacio.setPhoto(
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
      userIgnacio.setRoles(rolesAdmin);

      User userAlexis = new User();
      userAlexis.setFirstName("Alexis");
      userAlexis.setLastName("Bahi");
      userAlexis.setEmail("abahi@alkemy.com");
      userAlexis.setPassword(new BCryptPasswordEncoder().encode("abahi"));
      userAlexis.setPhoto(
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
      userAlexis.setRoles(rolesAdmin);

      User userJoaquin = new User();
      userJoaquin.setFirstName("Joaquin");
      userJoaquin.setLastName("Aman");
      userJoaquin.setEmail("jaman@alkemy.com");
      userJoaquin.setPassword(new BCryptPasswordEncoder().encode("jaman"));
      userJoaquin.setPhoto(
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
      userJoaquin.setRoles(rolesAdmin);

      User userKevin = new User();
      userKevin.setFirstName("Kevin");
      userKevin.setLastName("Lugo");
      userKevin.setEmail("klugo@alkemy.com");
      userKevin.setPassword(new BCryptPasswordEncoder().encode("klugo"));
      userKevin.setPhoto(
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
      userKevin.setRoles(rolesAdmin);

      User userLucio = new User();
      userLucio.setFirstName("Lucio");
      userLucio.setLastName("Scaceres");
      userLucio.setEmail("lscaceres@alkemy.com");
      userLucio.setPassword(new BCryptPasswordEncoder().encode("lscaceres"));
      userLucio.setPhoto(
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
      userLucio.setRoles(rolesAdmin);

      User userMatias = new User();
      userMatias.setFirstName("Matias");
      userMatias.setLastName("Cevini");
      userMatias.setEmail("mcevini@alkemy.com");
      userMatias.setPassword(new BCryptPasswordEncoder().encode("mcevini"));
      userMatias.setPhoto(
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
      userMatias.setRoles(rolesAdmin);

      User userOscar = new User();
      userOscar.setFirstName("Oscar");
      userOscar.setLastName("Ruina");
      userOscar.setEmail("oruina@alkemy.com");
      userOscar.setPassword(new BCryptPasswordEncoder().encode("oruina"));
      userOscar.setPhoto(
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
      userOscar.setRoles(rolesAdmin);

      User userMagali = new User();
      userMagali.setFirstName("Magali");
      userMagali.setLastName("Kain");
      userMagali.setEmail("mkain@alkemy.com");
      userMagali.setPassword(new BCryptPasswordEncoder().encode("mkain"));
      userMagali.setPhoto(
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
      userMagali.setRoles(rolesAdmin);

      User userPablo = new User();
      userPablo.setFirstName("Pablo");
      userPablo.setLastName("Samid");
      userPablo.setEmail("psamid@alkemy.com");
      userPablo.setPassword(new BCryptPasswordEncoder().encode("psamid"));
      userPablo.setPhoto(
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
      userPablo.setRoles(rolesAdmin);

      User userAlejandro = new User();
      userAlejandro.setFirstName("Alejandro");
      userAlejandro.setLastName("Ruiz");
      userAlejandro.setEmail("aruiz@alkemy.com");
      userAlejandro.setPassword(new BCryptPasswordEncoder().encode("aruiz"));
      userAlejandro.setPhoto(
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
      userAlejandro.setRoles(rolesAdmin);

      //------------------ User -------------------------

      User userMario = new User();
      userMario.setFirstName("Mario");
      userMario.setLastName("Ruiz");
      userMario.setEmail("mruiz@alkemy.com");
      userMario.setPassword(new BCryptPasswordEncoder().encode("mruiz"));
      userMario.setPhoto(
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
      userMario.setRoles(rolesUser);

      User userLucas = new User();
      userLucas.setFirstName("Lucas");
      userLucas.setLastName("Lopez");
      userLucas.setEmail("llopez@alkemy.com");
      userLucas.setPassword(new BCryptPasswordEncoder().encode("llopez"));
      userLucas.setPhoto(
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
      userLucas.setRoles(rolesUser);

      User userSantiago = new User();
      userSantiago.setFirstName("Santiago");
      userSantiago.setLastName("Tierno");
      userSantiago.setEmail("stierno@alkemy.com");
      userSantiago.setPassword(new BCryptPasswordEncoder().encode("stierno"));
      userSantiago.setPhoto(
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
      userSantiago.setRoles(rolesUser);

      User userJulieta = new User();
      userJulieta.setFirstName("Julieta");
      userJulieta.setLastName("Tierno");
      userJulieta.setEmail("jtierno@alkemy.com");
      userJulieta.setPassword(new BCryptPasswordEncoder().encode("jtierno"));
      userJulieta.setPhoto(
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
      userJulieta.setRoles(rolesUser);

      User userDaniela = new User();
      userDaniela.setFirstName("Daniela");
      userDaniela.setLastName("Tierno");
      userDaniela.setEmail("dtierno@alkemy.com");
      userDaniela.setPassword(new BCryptPasswordEncoder().encode("dtierno"));
      userDaniela.setPhoto(
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
      userDaniela.setRoles(rolesUser);

      User userNaiara = new User();
      userNaiara.setFirstName("Naiara");
      userNaiara.setLastName("Paez");
      userNaiara.setEmail("npaez@alkemy.com");
      userNaiara.setPassword(new BCryptPasswordEncoder().encode("npaez"));
      userNaiara.setPhoto(
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
      userNaiara.setRoles(rolesUser);

      User userJulian = new User();
      userJulian.setFirstName("Julian");
      userJulian.setLastName("Paez");
      userJulian.setEmail("jpaez@alkemy.com");
      userJulian.setPassword(new BCryptPasswordEncoder().encode("jpaez"));
      userJulian.setPhoto(
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
      userJulian.setRoles(rolesUser);

      User userJosias = new User();
      userJosias.setFirstName("Josias");
      userJosias.setLastName("Santoro");
      userJosias.setEmail("jsantoro@alkemy.com");
      userJosias.setPassword(new BCryptPasswordEncoder().encode("jsantoro"));
      userJosias.setPhoto(
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
      userJosias.setRoles(rolesUser);

      User userFederico = new User();
      userFederico.setFirstName("Federico");
      userFederico.setLastName("Santoro");
      userFederico.setEmail("fsantoro@alkemy.com");
      userFederico.setPassword(new BCryptPasswordEncoder().encode("fsantoro"));
      userFederico.setPhoto(
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
      userFederico.setRoles(rolesUser);

      User userEzequiel = new User();
      userEzequiel.setFirstName("Ezequiel");
      userEzequiel.setLastName("Santoro");
      userEzequiel.setEmail("esantoro@alkemy.com");
      userEzequiel.setPassword(new BCryptPasswordEncoder().encode("esantoro"));
      userEzequiel.setPhoto(
          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
      userEzequiel.setRoles(rolesUser);

      userRepository.saveAll(new ArrayList<User>(){{
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
      //logger.info("Users Seeded");
    } else {
      //logger.trace("Users Seeding Not Required");
    }
  }
}
