package com.alkemy.ong.repository;

import com.alkemy.ong.model.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMemberRepository extends JpaRepository<Member, Long> {

  Page<Member> findBySoftDeleteFalse(Pageable pageable);

}
