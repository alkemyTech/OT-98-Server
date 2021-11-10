package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.entity.Testimonial;
import org.springframework.data.domain.Page;

public interface IListTestimonialsService {

  Page<Testimonial> list(int page, int size);

}
