package com.alkemy.ong.service;

import com.alkemy.ong.common.converter.ConvertUtils;
import com.alkemy.ong.exception.EntityAlreadyExistException;
import com.alkemy.ong.model.entity.Category;
import com.alkemy.ong.model.request.CategoryUpdateRequest;
import com.alkemy.ong.model.request.CreateCategoryRequest;
import com.alkemy.ong.model.response.DetailsCategoryResponse;
import com.alkemy.ong.repository.ICategoryRepository;
import com.alkemy.ong.service.abstraction.ICreateCategoryService;
import com.alkemy.ong.service.abstraction.IDeleteCategoryService;
import com.alkemy.ong.service.abstraction.IGetCategoryService;
import com.alkemy.ong.service.abstraction.IListCategoryService;
import com.alkemy.ong.service.abstraction.IUpdateCategoryService;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryServiceImpl implements ICreateCategoryService, IListCategoryService,
    IGetCategoryService, IUpdateCategoryService, IDeleteCategoryService {

  @Autowired
  private ConvertUtils convertUtils;

  @Autowired
  private ICategoryRepository categoryRepository;

  @Override
  @Transactional
  public Category create(CreateCategoryRequest createCategoryRequest)
      throws EntityAlreadyExistException {
    throwErrorIfDoesNotExist(createCategoryRequest.getName());
    Category category = new Category();
    category.setName(createCategoryRequest.getName());
    category.setSoftDelete(false);
    categoryRepository.save(category);
    return category;
  }

  @Override
  @Transactional
  public Page<Category> findAll(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return categoryRepository.findBySoftDeleteFalse(pageable);
  }

  @Override
  public DetailsCategoryResponse getBy(Long id) throws EntityNotFoundException {
    Category category = categoryRepository.getById(id);
    validateCategory(category);
    return convertUtils.toDetailsCategoryResponseResponse(category);
  }

  @Override
  public DetailsCategoryResponse update(CategoryUpdateRequest categoryUpdateRequest, long id)
      throws EntityAlreadyExistException {
    throwErrorIfDoesNotExist(categoryUpdateRequest.getName());
    Category category = categoryRepository.getById(id);
    validateCategory(category);
    category.setName(categoryUpdateRequest.getName());
    category.setDescription(categoryUpdateRequest.getDescription());
    category.setImage(categoryUpdateRequest.getImage());
    categoryRepository.save(category);
    return convertUtils.toDetailsCategoryResponseResponse(category);
  }

  @Override
  public void deleteBy(long id) throws EntityNotFoundException {
    Optional<Category> optionalCategory = categoryRepository.findById(id);
    validateIfCategoryExists(optionalCategory);
    Category category = optionalCategory.get();
    validateCategory(category);
    category.setSoftDelete(true);
    categoryRepository.save(category);
  }

  private void validateCategory(Category category) {
    if (category == null || category.isSoftDelete()) {
      throw new EntityNotFoundException("The requested resource could not be found.");
    }
  }

  private void validateIfCategoryExists(Optional<Category> optionalCategory) {
    if (optionalCategory.isEmpty()) {
      throw new EntityNotFoundException("The requested resource could not be found.");
    }
  }

  private void throwErrorIfDoesNotExist(String name) throws EntityAlreadyExistException {
    if (categoryRepository.findByName(name) != null) {
      throw new EntityAlreadyExistException("category");
    }
  }

}
