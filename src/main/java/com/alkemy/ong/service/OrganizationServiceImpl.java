package com.alkemy.ong.service;

import com.alkemy.ong.model.response.OrganizationResponse;
import com.alkemy.ong.repository.IOrganizationRepository;
import com.alkemy.ong.service.abstraction.IOrganizationService;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OrganizationServiceImpl implements IOrganizationService {

  @Autowired
  private IOrganizationRepository iOrganizationRepository;

  @Override
  public List<OrganizationResponse> getOrganization() throws Exception {
     List<Organization> organization = iOrganizationRepository.findAll();
    OrganizationResponse response = OrganizationResponse.builder()
         .name(organization.get(0).getName())
         .image(organization.get(0).getImage())
         .address(organization.get(0).getAddress())
         .phone(organization.get(0).getPhone())
         .email(organization.get(0).getEmail())
         .welcomeText(organization.get(0).getWelcomeText())
         .aboutUsText(organization.get(0).getAboutUsText())
         .build();
     List<OrganizationResponse>organizations = new ArrayList<>();
     organizations.add(response);
    return organizations;
  }
}
