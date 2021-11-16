package com.alkemy.ong.repository;

import com.alkemy.ong.model.entity.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {

  Category findByName(String name);

  List<Category> findBySoftDeleteFalse();

}
