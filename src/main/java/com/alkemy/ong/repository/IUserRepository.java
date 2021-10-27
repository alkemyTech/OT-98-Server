package com.alkemy.ong.repository;

import com.alkemy.ong.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

  public User findByEmail(String email);
}
