package com.alkemy.ong.service;

import com.alkemy.ong.model.entity.Activity;
import com.alkemy.ong.model.request.ActivityDetailsRequest;
import com.alkemy.ong.repository.IActivityRepository;
import com.alkemy.ong.service.abstraction.ICreateActivityService;
import com.alkemy.ong.service.abstraction.IUpdateActivityService;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImpl implements ICreateActivityService, IUpdateActivityService {

  @Autowired
  IActivityRepository activityRepository;

  @Override
  public Activity create(ActivityDetailsRequest createActivityRequest) {
    Activity activity = new Activity();
    activity.setName(createActivityRequest.getName());
    activity.setContent(createActivityRequest.getContent());
    activity.setImage(createActivityRequest.getImage());
    activity.setSoftDelete(false);
    activityRepository.save(activity);
    return activity;
  }


  @Override
  public Activity update(long id, ActivityDetailsRequest activityDetailsRequest) {
    Optional<Activity> activityOptional = activityRepository.findById(id);

    if (activityOptional.isEmpty()) {
      throw new EntityNotFoundException("Activity not found");
    }

    Activity activity = activityOptional.get();
    activity.setName(activityDetailsRequest.getName());
    activity.setContent(activityDetailsRequest.getContent());
    activity.setImage(activityDetailsRequest.getImage());
    activityRepository.save(activity);

    return activity;

  }
}
