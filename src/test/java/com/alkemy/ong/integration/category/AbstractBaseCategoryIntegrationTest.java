package com.alkemy.ong.integration.category;

import com.alkemy.ong.common.AbstractBaseIntegrationTest;
import com.alkemy.ong.model.entity.Category;
import com.alkemy.ong.model.request.CategoryUpdateRequest;
import com.alkemy.ong.model.request.CreateCategoryRequest;
import com.alkemy.ong.repository.ICategoryRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.test.mock.mockito.MockBean;

public abstract class AbstractBaseCategoryIntegrationTest extends AbstractBaseIntegrationTest {

  @MockBean
  protected ICategoryRepository categoryRepository;

  protected Category stubCategory() {
    return new Category(
        1L,
        "CategoryTest",
        "CategoryDescriptionTest",
        "CategoryTestImage.jpg",
        null,
        false
    );
  }

  protected CreateCategoryRequest exampleCategoryRequest() {
    CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest();
    createCategoryRequest.setName("exampleCategory");
    return createCategoryRequest;
  }

  protected CategoryUpdateRequest updateCategoryRequest() {
    CategoryUpdateRequest categoryUpdateRequest = new CategoryUpdateRequest();
    categoryUpdateRequest.setName("nameEdited");
    categoryUpdateRequest.setDescription("descEdited");
    categoryUpdateRequest.setImage("imageEdited.png");
    return categoryUpdateRequest;
  }

  protected List<Category> stubListCategory(int count) {
    List<Category> categories = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      categories.add(new Category(
          1L,
          "CategoryTest",
          "CategoryDescriptionTest",
          "CategoryTestImage.jpg",
          null,
          false
      ));
    }
    return categories;
  }

}
