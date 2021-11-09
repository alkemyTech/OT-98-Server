package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.request.OrganizationDetailsRequest;
import com.alkemy.ong.model.response.OrganizationResponse;
import javax.persistence.EntityNotFoundException;

public interface IOrganizationService {

  OrganizationResponse getOrganizationDetails();

  void update(OrganizationDetailsRequest organizationDetailsRequest) throws EntityNotFoundException;

}
