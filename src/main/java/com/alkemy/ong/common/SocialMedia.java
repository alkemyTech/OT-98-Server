package com.alkemy.ong.common;

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

  @Column(name = "URL_FACEBOOK")
  private String urlFacebook;

  @Column(name = "URL_LINKEDIN")
  private String urlLinkedin;

  @Column(name = "URL_INSTAGRAM")
  private String urlInstagram;

  public void setUrlFacebook(String urlFacebook) {
    this.urlFacebook = "https://www.facebook.com/" + urlFacebook;
  }

  public void setUrlLinkedin(String urlLinkedin) {
    this.urlLinkedin = "https://www.linkedin.com/in/" + urlLinkedin;
  }

  public void setUrlInstagram(String urlInstagram) {
    this.urlInstagram = "https://www.instagram.com/" + urlInstagram;
  }
}
