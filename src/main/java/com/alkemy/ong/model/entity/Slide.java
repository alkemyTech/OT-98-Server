package com.alkemy.ong.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SLIDES")
public class Slide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SLIDES_ID", nullable = false)
    private long id;

    @Column(name = "IMAGE_URL")
    private String image_Url;

    @Column(name = "TEXT")
    private String text;

    @Column(name = "ORDER")
    private int order;

    @JoinColumn(name = "ORGANIZATION_ID")
    @ManyToOne(fetch = FetchType.EAGER)
    private Organization organizationId;
}
