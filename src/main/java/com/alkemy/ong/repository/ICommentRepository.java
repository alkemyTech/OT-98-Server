package com.alkemy.ong.repository;

import com.alkemy.ong.model.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long> {

  @Override
  @Query("from Comment c order by c.timestamp")
  List<Comment> findAll();

  @Query(value = "from Comment c where c.newsId.id = :id")
  List<Comment> findByNewsId(@Param("id") Long id);
}
