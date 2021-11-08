package com.alkemy.ong.common.converter;

import com.alkemy.ong.model.entity.Activity;
import com.alkemy.ong.model.entity.Category;
import com.alkemy.ong.model.entity.Contact;
import com.alkemy.ong.model.entity.News;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.response.CreateActivityResponse;
import com.alkemy.ong.model.response.CreateCategoryResponse;
import com.alkemy.ong.model.response.NewsDetailsResponse;
import com.alkemy.ong.model.response.DetailsContactResponse;
import com.alkemy.ong.model.response.NewsCategoryResponse;
import com.alkemy.ong.model.response.UserRegisterResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

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

  public NewsDetailsResponse createToResponse(News news) {
    NewsDetailsResponse createNewsResponse = new NewsDetailsResponse();
    createNewsResponse.setName(news.getName());
    createNewsResponse.setContent(news.getContent());
    createNewsResponse.setImage(news.getImage());
    createNewsResponse.setCategory(news.getCategory().getName());
    return createNewsResponse;
  }

  public DetailsContactResponse toResponse(Contact contact) {
    DetailsContactResponse detailsContactResponse = new DetailsContactResponse();
    detailsContactResponse.setId(contact.getId());
    detailsContactResponse.setName(contact.getName());
    detailsContactResponse.setPhone(contact.getPhone());
    detailsContactResponse.setEmail(contact.getEmail());
    detailsContactResponse.setMessage(contact.getMessage());
    return detailsContactResponse;
  }

  public List<DetailsContactResponse> toResponse(List<Contact> contacts) {
    List<DetailsContactResponse> detailsContactResponses = new ArrayList<>();
    contacts.forEach(contact -> {
      detailsContactResponses.add(toResponse(contact)
      );
    });
    return detailsContactResponses;
  }

  public NewsDetailsResponse getToResponse(News news) {
    NewsDetailsResponse getNewsResponse = new NewsDetailsResponse();
    getNewsResponse.setId(news.getId());
    getNewsResponse.setName(news.getName());
    getNewsResponse.setContent(news.getContent());
    getNewsResponse.setImage(news.getImage());
    getNewsResponse.setNewsCategory(this.newsCategorytoResponse(news));
    return getNewsResponse;
  }

  private NewsCategoryResponse newsCategorytoResponse(News news) {
    NewsCategoryResponse newsCategoryResponse = new NewsCategoryResponse();
    newsCategoryResponse.setName(news.getCategory().getName());
    newsCategoryResponse.setDescription(news.getCategory().getDescription());
    newsCategoryResponse.setImage(news.getCategory().getImage());
    return newsCategoryResponse;
  }
}
