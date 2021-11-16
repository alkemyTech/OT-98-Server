package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.response.DetailsCategoryResponse;
import javax.persistence.EntityNotFoundException;

public interface IGetCategoryService {

  DetailsCategoryResponse getBy(Long id) throws EntityNotFoundException;

}
