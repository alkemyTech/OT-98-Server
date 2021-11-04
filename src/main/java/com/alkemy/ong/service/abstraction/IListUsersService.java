package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.model.response.UserResponse;
import java.util.List;

public interface IListUsersService {

    List<UserResponse>  listActiveUsers();
}
