package com.alkemy.ong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.alkemy.ong.model.entity.Comment;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long> {

}
