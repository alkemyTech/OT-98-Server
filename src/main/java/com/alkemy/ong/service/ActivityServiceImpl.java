package com.alkemy.ong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alkemy.ong.model.entity.Activity;
import com.alkemy.ong.model.request.CreateActivityRequest;
import com.alkemy.ong.repository.IActivityRepository;
import com.alkemy.ong.service.abstraction.ICreateActivityService;

@Service
public class ActivityServiceImpl implements ICreateActivityService {

  @Autowired
  IActivityRepository iActivityRepository;

  @Override
  public Activity create(CreateActivityRequest createActivityRequest) {
    // TODO Auto-generated method stub
    Activity activity = new Activity();
    activity.setName(createActivityRequest.getName());
    activity.setContent(createActivityRequest.getContent());
    activity.setImage(createActivityRequest.getImage());
    activity.setSoftDelete(false);
    iActivityRepository.save(activity);
    return activity;
  }
}
