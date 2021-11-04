package com.alkemy.ong.repository.repositoryImpl;

import com.alkemy.ong.model.entity.User;
import com.alkemy.ong.repository.IDeleteUserRepository;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DeleteUserRepositoryImpl implements IDeleteUserRepository {

  @Autowired
  private EntityManager entityManager;

  @Override
  public void deleteById(Long id) {
    String query = "UPDATE USERS SET SOFT_DELETED = 'TRUE' WHERE ID = :ID";
    try {
      entityManager.createQuery(query, User.class)
          .setParameter("id", id);
    } catch (Exception e) {
      throw new EntityNotFoundException();
    }
  }
}
