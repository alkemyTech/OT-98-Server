package com.alkemy.ong.repository;

import com.alkemy.ong.model.entity.Comment;
import com.alkemy.ong.model.entity.News;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long> {

  @Override
  @Query("from Comment c order by c.timestamp")
  List<Comment> findAll();

  List<Comment> findByNewsId(Optional<News> news);
}
