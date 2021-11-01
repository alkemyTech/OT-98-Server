package com.alkemy.ong.common.converter;

import com.alkemy.ong.model.entity.Category;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.response.CategoryCreationResponse;
import com.alkemy.ong.model.response.UserRegisterResponse;
import org.springframework.stereotype.Component;

@Component("convertUtils")
public class ConvertUtils {

  public UserRegisterResponse toResponse(User user) {
    UserRegisterResponse userRegisterResponse = new UserRegisterResponse();
    userRegisterResponse.setId(user.getId());
    userRegisterResponse.setFirstsName(user.getFirstName());
    userRegisterResponse.setLastName(user.getLastName());
    userRegisterResponse.setEmail(user.getEmail());
    return userRegisterResponse;
  }

  public CategoryCreationResponse toResponse(Category category) {
    CategoryCreationResponse categoryCreationResponse = new CategoryCreationResponse();
    categoryCreationResponse.setId(category.getId());
    categoryCreationResponse.setName(category.getName());
    return categoryCreationResponse;
  }

}
