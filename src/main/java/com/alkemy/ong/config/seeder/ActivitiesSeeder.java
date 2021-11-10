package com.alkemy.ong.config.seeder;

import java.sql.Timestamp;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.alkemy.ong.model.entity.Activity;
import com.alkemy.ong.repository.IActivityRepository;

@Component
public class ActivitiesSeeder implements CommandLineRunner {

  private static final String IMAGE = "https://foo.jpg";

  @Autowired
  IActivityRepository activityRepository;

  @Override
  public void run(String... args) throws Exception {
    loadActivities();
  }

  private void loadActivities() {
    if (activityRepository.count() == 0) {
      loadActivitiesSeed();
    }
  }

  private void loadActivitiesSeed() {
    activityRepository.save(buildActivity(1L, "actividadUno", "contenidoUno"));
    activityRepository.save(buildActivity(2L, "actividadDos", "contenidoDos"));
    activityRepository.save(buildActivity(3L, "actividadTres", "contenidoTres"));
    activityRepository.save(buildActivity(4L, "actividadCuatro", "contenidoCuatro"));
    activityRepository.save(buildActivity(5L, "actividadCinco", "contenidoCinco"));
    activityRepository.save(buildActivity(6L, "actividadSeis", "contenidoSeis"));
    activityRepository.save(buildActivity(7L, "actividadSiete", "contenidoSiete"));
    activityRepository.save(buildActivity(8L, "actividadOcho", "contenidoOcho"));
    activityRepository.save(buildActivity(9L, "actividadNueve", "contenidoNueve"));
    activityRepository.save(buildActivity(10L, "actividadDiez", "contenidoDiez"));
  }

  private Activity buildActivity(long id, String name, String content) {
    return new Activity(id, 
        name, 
        content, 
        IMAGE, 
        Timestamp.from(Instant.now()), 
        false);
  }

}
