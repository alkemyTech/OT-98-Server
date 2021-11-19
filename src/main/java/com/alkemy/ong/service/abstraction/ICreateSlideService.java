package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.exception.ExternalServiceException;
import com.alkemy.ong.model.entity.Slide;
import com.alkemy.ong.model.request.CreateSlideRequest;
import javax.persistence.EntityNotFoundException;

public interface ICreateSlideService {

  Slide create(CreateSlideRequest createSlideRequest)
      throws EntityNotFoundException, ExternalServiceException;

}
