package com.alkemy.ong.service;

import com.alkemy.ong.model.entity.Organization;
import com.alkemy.ong.model.request.OrganizationDetailsRequest;
import com.alkemy.ong.model.response.OrganizationResponse;
import com.alkemy.ong.repository.IOrganizationRepository;
import com.alkemy.ong.service.abstraction.IOrganizationService;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements IOrganizationService {

  @Autowired
  private IOrganizationRepository organizationRepository;

  @Override
  @Transactional
  public OrganizationResponse getOrganizationDetails() {
    Organization organization = getOrganization();

    return OrganizationResponse.builder()
        .name(organization.getName())
        .image(organization.getImage())
        .address(organization.getAddress())
        .phone(organization.getPhone())
        .facebookUrl(organization.getFacebookUrl())
        .instagramUrl(organization.getInstagramUrl())
        .linkedinUrl(organization.getLinkedinUrl())
        .build();

  }

  @Override
  @Transactional
  public void update(OrganizationDetailsRequest organizationDetailsRequest)
      throws EntityNotFoundException {
    Organization organization = getOrganization();
    organization.setName(organizationDetailsRequest.getName());
    organization.setImage(organizationDetailsRequest.getImage());
    organization.setAddress(organizationDetailsRequest.getAddress());
    organization.setPhone(organizationDetailsRequest.getPhone());
    organization.setEmail(organizationDetailsRequest.getEmail());
    organization.setWelcomeText(organizationDetailsRequest.getWelcomeText());
    organization.setAboutUsText(organizationDetailsRequest.getAboutUsText());
    organization.setFacebookUrl(organizationDetailsRequest.getFacebookUrl());
    organization.setLinkedinUrl(organizationDetailsRequest.getLinkedinUrl());
    organization.setInstagramUrl(organizationDetailsRequest.getInstagramUrl());
    organizationRepository.save(organization);
  }

  private Organization getOrganization() {
    List<Organization> organizations = organizationRepository.findAll();
    if (organizations.isEmpty()) {
      throw new EntityNotFoundException("The requested resource could not be found.");
    }
    return organizations.get(0);
  }

}
