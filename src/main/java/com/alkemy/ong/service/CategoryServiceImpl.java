package com.alkemy.ong.service;

import com.alkemy.ong.exception.EntityAlreadyExistException;
import com.alkemy.ong.model.entity.Category;
import com.alkemy.ong.model.request.CreateCategoryRequest;
import com.alkemy.ong.model.response.ListCategoryResponse;
import com.alkemy.ong.repository.ICategoryRepository;
import com.alkemy.ong.service.abstraction.ICreateCategoryService;
import com.alkemy.ong.service.abstraction.IListCategoryService;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryServiceImpl implements ICreateCategoryService, IListCategoryService {

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
  @Transactional
  public List<ListCategoryResponse> findAll() {
    List<Category> categories = categoryRepository.findAll();
    validateCategories(categories);
    ModelMapper modelMapper = new ModelMapper();
    List<ListCategoryResponse> listCategoryResponses = new ArrayList<>();
    for (Category category : categories) {
      listCategoryResponses.add(modelMapper.map(category, ListCategoryResponse.class));
    }
    return listCategoryResponses;
  }

  private void validateCategories(List<Category> categories) {
    if (categories.isEmpty()) {
      throw new EntityNotFoundException("The requested resource could not be found.");
    }

  }
}
