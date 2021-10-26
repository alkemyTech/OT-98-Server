package com.alkemy.ong.model.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="ORGANIZATIONS")
public class Organization {

	@Id
	@Column(name="ORGANIZATIONS_ID")
	@Setter(AccessLevel.NONE)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="NAME",nullable = false)
	private String name;
	
	@Column(name="IMAGE",nullable = false)
	private String image;
	
	@Column(name="ADDRESS",nullable = true)
	private String address;
	
	@Column(name="PHONE",nullable = true)
	private Integer phone;
	
	@Column(name="EMAIL",nullable = false)
	private String email;
	
	@Column(name="WELCOME_TEXT",nullable = false)
	private String welcomeText;
	
	@Column(name="ABOUT_US_TEXT",nullable = true)
	private String aboutUsText;
	
	@Column(name="TIMESTAMP")
	@CreationTimestamp
	private Timestamp timeStamp;
	
	@Column(name="SOFT_DELETE")
	private boolean softDelete;
	
	
}
