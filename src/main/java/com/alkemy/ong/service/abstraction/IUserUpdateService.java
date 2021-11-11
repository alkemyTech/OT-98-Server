package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.request.UserFirtstNameRequest;
import com.alkemy.ong.model.request.UserLastNameRequest;
import com.alkemy.ong.model.request.UserUpdateRequest;
import com.alkemy.ong.model.response.UserDetailsResponse;
import java.util.Map;
import org.springframework.data.jpa.repository.Query;

public interface IUserUpdateService {

  UserDetailsResponse update(long id, UserUpdateRequest userUpdateRequest);

}
