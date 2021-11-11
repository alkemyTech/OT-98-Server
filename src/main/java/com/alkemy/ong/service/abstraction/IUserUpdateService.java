package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.entity.User;
import java.util.Map;

public interface IUserUpdateService {

  User update(Map<Object, Object> fields, long id);

}
