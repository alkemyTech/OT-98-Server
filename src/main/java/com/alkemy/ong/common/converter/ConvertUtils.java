package com.alkemy.ong.common.converter;

import org.springframework.stereotype.Component;
import com.alkemy.ong.model.entity.Activity;
import com.alkemy.ong.model.entity.Category;
import com.alkemy.ong.model.entity.News;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.response.CreateActivityResponse;
import com.alkemy.ong.model.response.CreateCategoryResponse;
import com.alkemy.ong.model.response.CreateNewsResponse;
import com.alkemy.ong.model.response.UserRegisterResponse;

@Component("convertUtils")
public class ConvertUtils {

  public UserRegisterResponse toResponse(User user, String jwt) {
    UserRegisterResponse userRegisterResponse = new UserRegisterResponse();
    userRegisterResponse.setId(user.getId());
    userRegisterResponse.setFirstsName(user.getFirstName());
    userRegisterResponse.setLastName(user.getLastName());
    userRegisterResponse.setEmail(user.getEmail());
    userRegisterResponse.setJwt(jwt);
    return userRegisterResponse;
  }

  public CreateCategoryResponse toResponse(Category category) {
    CreateCategoryResponse createCategoryResponse = new CreateCategoryResponse();
    createCategoryResponse.setId(category.getId());
    createCategoryResponse.setName(category.getName());
    return createCategoryResponse;
  }

  public CreateActivityResponse toResponse(Activity activity) {
    CreateActivityResponse createActivityResponse = new CreateActivityResponse();
    createActivityResponse.setId(activity.getId());
    createActivityResponse.setName(activity.getName());
    createActivityResponse.setContent(activity.getContent());
    createActivityResponse.setImage(activity.getImage());
    return createActivityResponse;
  }

  public CreateNewsResponse toResponse(News news) {
    CreateNewsResponse createNewsResponse = new CreateNewsResponse();
    createNewsResponse.setName(news.getName());
    createNewsResponse.setContent(news.getContent());
    createNewsResponse.setImage(news.getImage());
    createNewsResponse.setCategory(news.getCategory().getName());
    return createNewsResponse;
  }
}
