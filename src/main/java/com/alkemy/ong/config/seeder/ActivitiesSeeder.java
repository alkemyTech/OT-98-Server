package com.alkemy.ong.config.seeder;

import com.alkemy.ong.model.entity.Activity;
import com.alkemy.ong.repository.IActivityRepository;
import java.sql.Timestamp;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ActivitiesSeeder implements CommandLineRunner {

  private static final String IMAGE = "https://foo.jpg";

  @Autowired
  private IActivityRepository activityRepository;

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
    activityRepository.save(buildActivity(1L, "School and family support", "Family support"));
    activityRepository.save(buildActivity(2L, "Elementary school support", "Elementary school"));
    activityRepository.save(buildActivity(3L, "Middle school support", "Middle school"));
    activityRepository.save(buildActivity(4L, "Hight school support", "Hight school"));
    activityRepository.save(buildActivity(5L, "Tutorials", "Tutorials"));
    activityRepository.save(buildActivity(6L, "School support", "School support"));
    activityRepository.save(buildActivity(7L, "Story's workshop", "Workshop"));
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
