package com.alkemy.ong.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "SLIDES")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Slide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SLIDES_ID", nullable = false)
    @Setter(AccessLevel.NONE)
    private long id;

    @Column(name = "IMAGE_URL")
    private String image_Url;

    @Column(name = "TEXT")
    private String text;

    @Column(name = "SLIDE_ORDER")
    private int order;

    @JoinColumn(name = "ORGANIZATION_ID")
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private Organization organizationId;
}
