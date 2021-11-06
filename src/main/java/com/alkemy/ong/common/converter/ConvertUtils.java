package com.alkemy.ong.common.converter;

import com.alkemy.ong.model.entity.Category;
import com.alkemy.ong.model.entity.Contact;
import com.alkemy.ong.model.entity.Testimonial;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.response.CreateCategoryResponse;
import com.alkemy.ong.model.response.CreateContactResponse;
import com.alkemy.ong.model.response.CreateTestimonialResponse;
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

  public CreateCategoryResponse toResponse(Category category) {
    CreateCategoryResponse createCategoryResponse = new CreateCategoryResponse();
    createCategoryResponse.setId(category.getId());
    createCategoryResponse.setName(category.getName());
    return createCategoryResponse;
  }

  public CreateContactResponse toResponse(Contact contact){
    CreateContactResponse  createContactResponse= new CreateContactResponse();
    createContactResponse.setId(contact.getId());
    createContactResponse.setName(contact.getName());
    createContactResponse.setPhone(contact.getPhone());
    createContactResponse.setEmail(contact.getEmail());
    createContactResponse.setMessage(contact.getMessage());
    return createContactResponse;
  }

}
