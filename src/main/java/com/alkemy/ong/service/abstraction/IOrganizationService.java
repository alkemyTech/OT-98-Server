package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.entity.Organization;
import com.alkemy.ong.model.response.OrganizationResponse;
import javax.persistence.EntityNotFoundException;

public interface IOrganizationService {

  OrganizationResponse getOrganizationDetails();
  void update(Organization organization) throws EntityNotFoundException;

}
