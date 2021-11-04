package com.alkemy.ong.model.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SocialMedia {

  @Column(name = "FACEBOOK_URL")
  private String facebookUrl;

  @Column(name = "LINKEDIN_URL")
  private String linkedinUrl;

  @Column(name = "INSTAGRAM_URL")
  private String instagramUrl;

  public void setFacebookUrl(String accountId) {
    this.facebookUrl = "https://www.facebook.com/" + accountId;
  }

  public void setLinkedinUrl(String accountId) {
    this.linkedinUrl = "https://www.linkedin.com/in/" + accountId;
  }

  public void setInstagramUrl(String accountId) {
    this.instagramUrl = "https://www.instagram.com/" + accountId;
  }
}
