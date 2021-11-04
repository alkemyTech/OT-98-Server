package com.alkemy.ong.service;

import com.alkemy.ong.model.entity.Activity;
import com.alkemy.ong.model.request.CreateActivityRequest;
import com.alkemy.ong.repository.IActivityRepository;
import com.alkemy.ong.service.abstraction.ICreateActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImpl implements ICreateActivityService {

  @Autowired
  IActivityRepository activityRepository;

  @Override
  public Activity create(CreateActivityRequest createActivityRequest) {
    Activity activity = new Activity();
    activity.setName(createActivityRequest.getName());
    activity.setContent(createActivityRequest.getContent());
    activity.setImage(createActivityRequest.getImage());
    activity.setSoftDelete(false);
    activityRepository.save(activity);
    return activity;
  }
}
