package com.alkemy.ong.repository;

import com.alkemy.ong.model.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

}
