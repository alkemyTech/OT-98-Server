package com.alkemy.ong.common.converter;

import com.alkemy.ong.model.entity.Activity;
import com.alkemy.ong.model.entity.Category;
import com.alkemy.ong.model.entity.Contact;
import com.alkemy.ong.model.entity.News;
import com.alkemy.ong.model.entity.Testimonial;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.response.CreateActivityResponse;
import com.alkemy.ong.model.response.CreateCategoryResponse;
import com.alkemy.ong.model.response.CreateContactResponse;
import com.alkemy.ong.model.response.CreateNewsResponse;
import com.alkemy.ong.model.response.CreateTestimonialResponse;
import com.alkemy.ong.model.response.UserRegisterResponse;
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

  public CreateNewsResponse toResponse(News news) {
    CreateNewsResponse createNewsResponse = new CreateNewsResponse();
    createNewsResponse.setName(news.getName());
    createNewsResponse.setContent(news.getContent());
    createNewsResponse.setImage(news.getImage());
    createNewsResponse.setCategory(news.getCategory().getName());
    return createNewsResponse;
  }

  public CreateContactResponse toResponse(Contact contact) {
    CreateContactResponse createContactResponse = new CreateContactResponse();
    createContactResponse.setId(contact.getId());
    createContactResponse.setName(contact.getName());
    createContactResponse.setPhone(contact.getPhone());
    createContactResponse.setEmail(contact.getEmail());
    createContactResponse.setMessage(contact.getMessage());
    return createContactResponse;
  }

  public CreateTestimonialResponse toResponse(Testimonial testimonial) {
    CreateTestimonialResponse createTestimonialResponse = new CreateTestimonialResponse();
    createTestimonialResponse.setId(testimonial.getId());
    createTestimonialResponse.setName(testimonial.getName());
    createTestimonialResponse.setImage(testimonial.getImage());
    createTestimonialResponse.setContent(testimonial.getContent());
    return createTestimonialResponse;
  }
}
