package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.entity.Category;
import com.alkemy.ong.model.request.CategoryCreationRequest;

public interface ICategoryCreationService {

  Category creation(CategoryCreationRequest categoryCreationRequest);

}
