package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.entity.Activity;
import com.alkemy.ong.model.request.ActivityUpdateRequest;
import com.alkemy.ong.model.response.UpdateActivityResponse;

public interface IUpdateActivityService {

 Activity update(long id, ActivityUpdateRequest activityUpdateRequest);

}
