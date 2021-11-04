package com.alkemy.ong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.alkemy.ong.model.entity.Activity;

@Repository
public interface IActivityRepository extends JpaRepository<Activity, Long> {

}
