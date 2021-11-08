package com.alkemy.ong.common;

import com.alkemy.ong.common.validation.EmailValidation;
import com.alkemy.ong.model.entity.Organization;

public class OrganizationUtil {

  private OrganizationUtil() {
  }

  public static Organization organizationFields(Organization o, Organization organization) {

    if (!(organization.getName() == null || organization.getName().isEmpty()))
      o.setName(organization.getName());

    if (!(organization.getImage() == null || organization.getImage().isEmpty()))
      o.setImage(organization.getImage());

    if (!(organization.getAddress() == null || organization.getAddress().isEmpty()))
      o.setAddress(organization.getAddress());

    if (!(organization.getPhone() == null))
      o.setPhone(organization.getPhone());

    if (EmailValidation.isValid(organization.getEmail()))
      o.setEmail(organization.getEmail());

    if (!(organization.getWelcomeText() == null || organization.getWelcomeText().isEmpty()))
      o.setWelcomeText(organization.getWelcomeText());

    if (!(organization.getAboutUsText() == null || organization.getAboutUsText().isEmpty()))
      o.setAboutUsText(organization.getAboutUsText());

    if (!(organization.getFacebookUrl() == null || organization.getFacebookUrl().isEmpty()
        || organization.getFacebookUrl().startsWith("https://www.facebook.com/")))
      o.setFacebookUrl(organization.getFacebookUrl());

    if (!(organization.getLinkedinUrl() == null || organization.getLinkedinUrl().isEmpty()
        || organization.getLinkedinUrl().startsWith("https://www.linkedin.com/in/")))
      o.setLinkedinUrl(organization.getLinkedinUrl());

    if (!(organization.getInstagramUrl() == null || organization.getInstagramUrl().isEmpty()
        || organization.getInstagramUrl().startsWith("https://www.instagram.com/")))
      o.setInstagramUrl(organization.getInstagramUrl());

    return o;
  }
}
