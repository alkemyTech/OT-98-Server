package com.alkemy.ong.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "CONTACTS")
@SQLDelete(sql = "UPDATE CONTACTS SET DELETED_AT = true WHERE id=?")
@Where(clause = "deleted=false")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTACTS_ID", nullable = false)
    private long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "DELETED_AT")
    private boolean deletedAd= Boolean.FALSE;



}
