package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.exception.ExternalServiceException;
import com.alkemy.ong.model.entity.Slide;
import com.alkemy.ong.model.request.CreateSlideRequest;
import javax.persistence.EntityNotFoundException;
import org.apache.http.entity.ContentType;

public interface ICreateSlideService {

  Slide create(ContentType contentType, String fileName, CreateSlideRequest createSlideRequest)
      throws EntityNotFoundException, ExternalServiceException, NullPointerException;

}
