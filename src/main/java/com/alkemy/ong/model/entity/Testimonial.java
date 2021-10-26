package com.alkemy.ong.model.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "TESTIMONIALS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Testimonial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "TESTIMONIALS_ID")
    private long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "IMAGE")
    private String image;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "TIMESTAMPS")
    @CreationTimestamp
    private Timestamp timestamps;

    @Column(name = "SOFT_DELETE")
    private boolean softDelete;
}
