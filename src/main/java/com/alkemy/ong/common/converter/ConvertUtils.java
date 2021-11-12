package com.alkemy.ong.common.converter;


import com.alkemy.ong.model.entity.Activity;
import com.alkemy.ong.model.entity.Category;
import com.alkemy.ong.model.entity.Comment;
import com.alkemy.ong.model.entity.Contact;
import com.alkemy.ong.model.entity.Member;
import com.alkemy.ong.model.entity.News;
import com.alkemy.ong.model.entity.Testimonial;
import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.response.CreateActivityResponse;
import com.alkemy.ong.model.response.CreateCategoryResponse;
import com.alkemy.ong.model.response.CreateCommentResponse;
import com.alkemy.ong.model.response.CreateTestimonialResponse;
import com.alkemy.ong.model.response.DetailsContactResponse;
import com.alkemy.ong.model.response.DetailsMemberResponse;
import com.alkemy.ong.model.response.ListNewsResponse;
import com.alkemy.ong.model.response.ListTestimonialResponse;
import com.alkemy.ong.model.response.NewsCategoryResponse;
import com.alkemy.ong.model.response.NewsDetailsResponse;
import com.alkemy.ong.model.response.TestimonialResponse;
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

  public CreateCommentResponse toResponse(Comment comment) {
    CreateCommentResponse createCommentResponse = new CreateCommentResponse();
    createCommentResponse.setId(comment.getId());
    createCommentResponse.setBody(comment.getBody());
    createCommentResponse.setUserId(comment.getUserId().getId());
    createCommentResponse.setNewsId(comment.getNewsId().getId());
    return createCommentResponse;
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

  public ListNewsResponse listNewsToResponse(List<News> news) {
    List<NewsDetailsResponse> newsResponse = new ArrayList<>();
    for (News newsItem : news) {
      newsResponse.add(getToResponse(newsItem));
    }
    return new ListNewsResponse(newsResponse);
  }

  public NewsDetailsResponse createToResponse(News news) {
    NewsDetailsResponse newsDetailsResponse = buildBaseNewsDetailsResponse(news);
    newsDetailsResponse.setCategory(news.getCategory().getName());
    return newsDetailsResponse;
  }

  public NewsDetailsResponse getToResponse(News news) {
    NewsDetailsResponse newsDetailsResponse = buildBaseNewsDetailsResponse(news);
    newsDetailsResponse.setId(news.getId());
    newsDetailsResponse.setNewsCategory(newsCategoryToResponse(news.getCategory()));
    return newsDetailsResponse;
  }

  private NewsCategoryResponse newsCategoryToResponse(Category category) {
    NewsCategoryResponse newsCategoryResponse = new NewsCategoryResponse();
    newsCategoryResponse.setName(category.getName());
    newsCategoryResponse.setDescription(category.getDescription());
    newsCategoryResponse.setImage(category.getImage());
    return newsCategoryResponse;
  }

  private NewsDetailsResponse buildBaseNewsDetailsResponse(News news) {
    NewsDetailsResponse newsDetailsResponse = new NewsDetailsResponse();
    newsDetailsResponse.setName(news.getName());
    newsDetailsResponse.setContent(news.getContent());
    newsDetailsResponse.setImage(news.getImage());
    return newsDetailsResponse;
  }

  public CreateTestimonialResponse toResponse(Testimonial testimonial) {
    CreateTestimonialResponse createTestimonialResponse = new CreateTestimonialResponse();
    createTestimonialResponse.setId(testimonial.getId());
    createTestimonialResponse.setName(testimonial.getName());
    createTestimonialResponse.setImage(testimonial.getImage());
    createTestimonialResponse.setContent(testimonial.getContent());
    return createTestimonialResponse;
  }

  public ListTestimonialResponse listTestimonialToResponse(List<Testimonial> testimonials) {
    List<TestimonialResponse> testimonialResponse = new ArrayList<>();
    for (Testimonial testimonial : testimonials) {
      testimonialResponse.add(getToResponse(testimonial));
    }
    return new ListTestimonialResponse(testimonialResponse);
  }

  private TestimonialResponse getToResponse(Testimonial testimonial) {
    TestimonialResponse testimonialResponse = new TestimonialResponse();
    testimonialResponse.setId(testimonial.getId());
    testimonialResponse.setName(testimonial.getName());
    testimonialResponse.setContent(testimonial.getContent());
    testimonialResponse.setImage(testimonial.getImage());
    return testimonialResponse;
  }

  public DetailsMemberResponse toResponse(Member member) {
    DetailsMemberResponse detailsMemberResponse = new DetailsMemberResponse();
    detailsMemberResponse.setId(member.getId());
    detailsMemberResponse.setName(member.getName());
    detailsMemberResponse.setImage(member.getImage());
    detailsMemberResponse.setDescription(member.getDescription());
    detailsMemberResponse.setTimestamp(member.getTimestamps());
    detailsMemberResponse.setFacebookUrl(member.getFacebookUrl());
    detailsMemberResponse.setLinkedinUrl(member.getLinkedinUrl());
    detailsMemberResponse.setInstagramUrl(member.getInstagramUrl());
    return detailsMemberResponse;
  }

  public List<DetailsMemberResponse> toResponseList(List<Member> members) {
    List<DetailsMemberResponse> detailsMemberResponses = new ArrayList<>(members.size());
    members.forEach(member -> {
      detailsMemberResponses.add(toResponse(member));
    });
    return detailsMemberResponses;
  }

  public CategoriesResponse categoryToResponse(Category category) {
    CategoriesResponse categoriesResponse = new CategoriesResponse();
    categoriesResponse.setName(category.getName());
    return categoriesResponse;
  }

  public List<CategoriesResponse> toCategoriesResponse(List<Category> categories) {
    List<CategoriesResponse> categoriesResponses = new ArrayList<>(categories.size());
    categories.forEach(category -> {
      categoriesResponses.add(categoryToResponse(category));
    });
    return categoriesResponses;
  }


  public DetailsCategoryResponse toDetailsCategoryResponseResponse(Category category) {
    DetailsCategoryResponse detailsCategoryResponse = new DetailsCategoryResponse();
    detailsCategoryResponse.setId(category.getId());
    detailsCategoryResponse.setName(category.getName());
    detailsCategoryResponse.setImage(category.getImage());
    detailsCategoryResponse.setDescription(category.getDescription());
    detailsCategoryResponse.setTimestamp(category.getTimestamp());
    return detailsCategoryResponse;
  }

}
