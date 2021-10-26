package com.alkemy.ong.model.entity;

import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="ORGANIZATIONS")
public class Organization {

	@Column(name="NAME")
	private String name;
	@Column(name="IMAGE")
	private String image;
	@Column(name="ADDRESS")
	private String address;
	@Column(name="PHONE")
	private Integer phone;
	@Column(name="EMAIL")
	private String email;
	@Column(name="WELCOME_TEXT")
	private String welcomeText;
	@Column(name="ABOUT_US_TEXT")
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
