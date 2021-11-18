package com.alkemy.ong.service.abstraction;

import javax.persistence.EntityNotFoundException;

public interface IDeleteTestimonialService {

  void deleteBy(Long id) throws EntityNotFoundException;
}
