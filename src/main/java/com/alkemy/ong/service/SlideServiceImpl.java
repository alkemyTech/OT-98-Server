package com.alkemy.ong.service;

import com.alkemy.ong.repository.ISlideRepository;
import com.alkemy.ong.service.abstraction.IDeleteSlideService;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SlideServiceImpl implements IDeleteSlideService {

  @Autowired
  private ISlideRepository slideRepository;

  @Transactional
  @Override
  public void delete(long id) throws EntityNotFoundException {
    if (!slideRepository.existsById(id)) {
      throw new EntityNotFoundException("Slide not found!");
    }
    slideRepository.deleteById(id);
  }

}
