package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.response.UserAuthenticatedMeResponse;

public interface IAuthenticatedUserDetails {

  UserAuthenticatedMeResponse getUserDetails(String authorizationHeader);

}
