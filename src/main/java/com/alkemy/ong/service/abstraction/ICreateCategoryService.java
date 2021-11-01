package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.exception.EntityAlreadyExistException;
import com.alkemy.ong.model.entity.Category;
import com.alkemy.ong.model.request.CreateCategoryRequest;

public interface ICreateCategoryService {

  Category create(CreateCategoryRequest createCategoryRequest)
      throws EntityAlreadyExistException;

}
