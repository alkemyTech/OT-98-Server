package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.response.OrganizationResponse;
import java.util.List;

public interface IOrganizationService {

  List<OrganizationResponse> getOrganization() throws Exception;

}
