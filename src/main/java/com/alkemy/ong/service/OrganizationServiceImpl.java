package com.alkemy.ong.service;

import com.alkemy.ong.model.entity.Organization;
import com.alkemy.ong.model.response.OrganizationResponse;
import com.alkemy.ong.repository.IOrganizationRepository;
import com.alkemy.ong.service.abstraction.IOrganizationService;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements IOrganizationService {

  @Autowired
  IOrganizationRepository organizationRepository;

  @Override
  @Transactional
  public OrganizationResponse getOrganizationDetails() {
    Organization organization = organizationRepository.findAll().get(0);
    if (organization == null) {
      throw new EntityNotFoundException("The requested resource could not be found.");
    }

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
}
