package com.alkemy.ong.service;

import com.alkemy.ong.model.entity.Role;
import com.alkemy.ong.repository.IRoleRepository;
import com.alkemy.ong.service.abstraction.IRoleService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService {

  private final IRoleRepository roleRepository;

  public RoleService(IRoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  public List<Role> findRoles() {
    return roleRepository.findAll();
  }

  @Override
  public Role findRoleById(long id) {
    return roleRepository.findById(id).orElseThrow();
  }

  @Override
  public Role findRoleByName(String name) {
    return roleRepository.findByName(name).orElseThrow();
  }
}
