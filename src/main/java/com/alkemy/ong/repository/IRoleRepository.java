package com.alkemy.ong.repository;

import com.alkemy.ong.model.entity.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findByName(String name);

}
