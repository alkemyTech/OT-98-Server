package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.entity.Slide;
import com.alkemy.ong.model.request.SlideDetailsRequest;

public interface IUpdateSlideService {

  Slide update(long id, SlideDetailsRequest slideDetailsRequest);

}
