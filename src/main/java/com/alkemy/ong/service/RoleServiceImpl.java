package com.alkemy.ong.service;

import com.alkemy.ong.model.entity.Role;
import com.alkemy.ong.repository.IRoleRepository;
import com.alkemy.ong.service.abstraction.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements IRoleService {

  @Autowired
  private IRoleRepository roleRepository;

  @Override
  public Role findBy(String name) {
    return roleRepository.findByName(name);
  }

}
