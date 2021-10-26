package com.alkemy.ong.model.entity;

import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="ORGANIZATIONS")
public class Organization {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
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
	private Timestamp timeStamp;
	@Column(name="SOFT_DELETE")
	private Boolean softDelete;
	
	public Organization(String name, String image, String address, Integer phone, String email, String welcomeText,
			String aboutUsText) {
		super();
		this.name = name;
		this.image = image;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.welcomeText = welcomeText;
		this.aboutUsText = aboutUsText;
		timeStamp= Timestamp.from(Instant.now());
	}



	public Organization() {
		super();
		timeStamp= Timestamp.from(Instant.now());
	}
	
	
}
