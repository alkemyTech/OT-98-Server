package com.alkemy.ong.service;

import com.alkemy.ong.exception.CategoryNameAlreadyExistException;
import com.alkemy.ong.model.entity.Category;
import com.alkemy.ong.model.request.CategoryCreationRequest;
import com.alkemy.ong.repository.ICategoryRepository;
import com.alkemy.ong.service.abstraction.ICategoryCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryServiceImpl implements ICategoryCreationService {

  @Autowired
  private ICategoryRepository categoryRepository;

  @Override
  @Transactional
  public Category creation(CategoryCreationRequest categoryCreationRequest)
      throws CategoryNameAlreadyExistException {
    if (categoryRepository.findByName(categoryCreationRequest.getName()) != null) {
      throw new CategoryNameAlreadyExistException();
    }
    Category category = new Category();
    category.setName(categoryCreationRequest.getName());
    category.setSoftDelete(false);
    categoryRepository.save(category);
    return category;
  }

}
