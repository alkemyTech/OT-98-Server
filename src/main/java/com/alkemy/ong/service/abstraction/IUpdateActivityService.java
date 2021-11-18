package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.entity.Activity;
import com.alkemy.ong.model.request.ActivityDetailsRequest;

public interface IUpdateActivityService {

 Activity update(long id, ActivityDetailsRequest activityDetailsRequest);

}
