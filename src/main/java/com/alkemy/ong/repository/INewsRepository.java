package com.alkemy.ong.repository;

import com.alkemy.ong.model.entity.Category;
import com.alkemy.ong.model.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface INewsRepository extends JpaRepository<News, Long> {

  @Query(value = "from Category c where c.name = :name")
  Category findCategoryByName(@Param("name") String name);

  Page<News> findBySoftDeleteIsFalse(Pageable pageable);

}
