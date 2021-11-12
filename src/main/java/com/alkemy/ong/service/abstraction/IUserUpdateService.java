package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.request.UserUpdateRequest;
import com.alkemy.ong.model.response.UserDetailsResponse;

public interface IUserUpdateService {

  UserDetailsResponse update(long id, UserUpdateRequest userUpdateRequest);

}
