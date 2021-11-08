package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.request.OrganizationRequest;
import com.alkemy.ong.model.response.OrganizationResponse;
import javax.persistence.EntityNotFoundException;

public interface IOrganizationService {

  OrganizationResponse getOrganizationDetails();
  void update(OrganizationRequest organization) throws EntityNotFoundException;

}
