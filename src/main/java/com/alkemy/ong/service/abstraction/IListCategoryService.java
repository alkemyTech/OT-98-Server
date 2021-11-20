package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.entity.Category;
import org.springframework.data.domain.Page;

public interface IListCategoryService {

  Page<Category> findAll(int page, int size);

}
