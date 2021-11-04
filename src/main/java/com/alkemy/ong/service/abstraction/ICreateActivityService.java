package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.entity.Activity;
import com.alkemy.ong.model.request.CreateActivityRequest;

public interface ICreateActivityService {

  Activity create(CreateActivityRequest createActivityRequest);
}
