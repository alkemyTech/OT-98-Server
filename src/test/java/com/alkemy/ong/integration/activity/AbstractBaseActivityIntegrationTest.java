package com.alkemy.ong.integration.activity;

import com.alkemy.ong.common.AbstractBaseIntegrationTest;
import com.alkemy.ong.model.entity.Activity;
import com.alkemy.ong.model.request.ActivityDetailsRequest;
import com.alkemy.ong.repository.IActivityRepository;
import org.springframework.boot.test.mock.mockito.MockBean;

public abstract class AbstractBaseActivityIntegrationTest extends AbstractBaseIntegrationTest {

  public final String emptyName = "The name attribute must not be empty.";
  public final String emptyContent = "The content attribute must not be empty.";
  public final String emptyImage = "The image attribute must not be empty.";

  @MockBean
  protected IActivityRepository activityRepository;

  protected Activity stubActivity() {
    return new Activity(
        1L,
        "Tutorials",
        "Tutorials",
        "image.png",
        null,
        false
    );
  }

  protected ActivityDetailsRequest exampleActivityRequest() {
    ActivityDetailsRequest activityDetailsRequest = new ActivityDetailsRequest();
    activityDetailsRequest.setName("Tutorials");
    activityDetailsRequest.setContent("Tutorials");
    activityDetailsRequest.setImage("image.png");
    return activityDetailsRequest;
  }

}
