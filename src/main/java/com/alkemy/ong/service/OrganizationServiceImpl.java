package com.alkemy.ong.service;

import com.alkemy.ong.model.entity.Organization;
import com.alkemy.ong.model.response.OrganizationResponse;
import com.alkemy.ong.repository.IOrganizationRepository;
import com.alkemy.ong.service.abstraction.IOrganizationService;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OrganizationServiceImpl implements IOrganizationService {

  @Autowired
  private IOrganizationRepository iOrganizationRepository;

  @Override
  public OrganizationResponse getOrganization() throws Exception {
     Optional<Organization>organizationOptional = iOrganizationRepository.findById(Long.valueOf(1));
     Organization organization = organizationOptional.get();
    OrganizationResponse response = OrganizationResponse.builder()
         .name(organization.getName())
         .image(organization.getImage())
         .address(organization.getAddress())
         .phone(organization.getPhone())
         .build();
    return response;
  }
}
