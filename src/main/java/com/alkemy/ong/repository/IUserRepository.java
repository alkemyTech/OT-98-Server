package com.alkemy.ong.repository;

import com.alkemy.ong.model.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

  User findByEmail(String email);

  List<User> findBySoftDeletedFalse();
}
