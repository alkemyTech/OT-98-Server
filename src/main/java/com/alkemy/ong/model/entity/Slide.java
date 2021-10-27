package com.alkemy.ong.model.entity;


import javax.persistence.*;

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
