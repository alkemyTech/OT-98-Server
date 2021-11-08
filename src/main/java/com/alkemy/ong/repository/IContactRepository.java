package com.alkemy.ong.repository;

import com.alkemy.ong.model.entity.Contact;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IContactRepository extends JpaRepository<Contact, Long> {

  List<Contact> findAll();

}
