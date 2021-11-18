package com.alkemy.ong.repository;

import com.alkemy.ong.model.entity.Slide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ISlideRepository extends JpaRepository<Slide, Long> {

  @Query(value = "SELECT MAX(SLIDE_ORDER) FROM SLIDES", nativeQuery = true)
  int getMaxOrder() throws NullPointerException;

}
