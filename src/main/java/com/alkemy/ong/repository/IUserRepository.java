package com.alkemy.ong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.alkemy.ong.model.entity.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
  public User findByEmail(String email);
}
