package com.alkemy.ong.service.abstraction;

import com.alkemy.ong.model.entity.Role;
import java.util.List;

public interface IRoleService {

  List<Role> findRoles();

  Role findRoleById(long id);

  Role findRoleByName(String name);

}
