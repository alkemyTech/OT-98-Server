package com.alkemy.ong.repository;

import com.alkemy.ong.model.entity.Slide;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ISlideRepository extends JpaRepository<Slide, Long> {

  @Query(value = "SELECT * FROM slides s WHERE s.organization_id = :organizationId ORDER BY s.slide_order",
      nativeQuery = true)
  List<Slide> findByOrganizationIdOrderBySlideOrder(@Param("organizationId") Long id);

  @Query(value = "SELECT MAX(SLIDE_ORDER) FROM SLIDES", nativeQuery = true)
  int getMaxOrder() throws NullPointerException;

}
