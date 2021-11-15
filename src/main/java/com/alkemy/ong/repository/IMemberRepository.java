package com.alkemy.ong.repository;

import com.alkemy.ong.model.entity.Member;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMemberRepository extends JpaRepository<Member, Long> {

  List<Member> findBySoftDeleteFalse();

  void deleteBy(long id) throws EntityNotFoundException;
}
