package com.alkemy.ong.service;

import com.alkemy.ong.exception.EntityAlreadyExistException;
import com.alkemy.ong.model.entity.Category;
import com.alkemy.ong.model.request.CreateCategoryRequest;
import com.alkemy.ong.model.response.DetailsCategoryResponse;
import com.alkemy.ong.repository.ICategoryRepository;
import com.alkemy.ong.service.abstraction.ICreateCategoryService;
import com.alkemy.ong.service.abstraction.IGetCategoryService;
import javax.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryServiceImpl implements ICreateCategoryService, IGetCategoryService {

  @Autowired
  private ICategoryRepository categoryRepository;

  @Override
  @Transactional
  public Category create(CreateCategoryRequest createCategoryRequest)
      throws EntityAlreadyExistException {
    if (categoryRepository.findByName(createCategoryRequest.getName()) != null) {
      throw new EntityAlreadyExistException("category");
    }
    Category category = new Category();
    category.setName(createCategoryRequest.getName());
    category.setSoftDelete(false);
    categoryRepository.save(category);
    return category;
  }

  @Override
  public DetailsCategoryResponse getBy(Long id) throws EntityNotFoundException {
    Category category = categoryRepository.getById(id);
    validateCategory(category);
    ModelMapper modelMapper = new ModelMapper();
    return modelMapper.map(category, DetailsCategoryResponse.class);
  }

  private void validateCategory(Category category) {
    if (category == null) {
      throw new EntityNotFoundException("The requested resource could not be found.");
    }
  }
}
