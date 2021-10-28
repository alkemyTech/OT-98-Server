package com.alkemy.ong.service;

import com.alkemy.ong.model.entity.Role;
import com.alkemy.ong.repository.IRoleRepository;
import com.alkemy.ong.service.abstraction.IRoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService {

  private final IRoleRepository roleRepository;

  public RoleService(IRoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  public Role findRoleByName(String name) {
    return roleRepository.findByName(name);
  }

}
