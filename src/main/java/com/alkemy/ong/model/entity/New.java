package com.alkemy.ong.model.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "news")
public class New {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "NEW_ID")
  @Setter(AccessLevel.NONE)
  private long id;

  @Column(name = "NAME", nullable = false)
  private String name;

  @Column(name = "CONTENT", nullable = false)
  private String content;

  @Column(name = "IMAGE", nullable = false)
  private String image;

  @ManyToOne
  @JoinColumn(name = "CATEGORY_ID")
  private Category category;

  @CreationTimestamp
  @Column(name = "TIMESTAMP")
  private Timestamp timestamp;

  @Column(name = "SOFT_DELETE")
  private boolean softDelete;

}
