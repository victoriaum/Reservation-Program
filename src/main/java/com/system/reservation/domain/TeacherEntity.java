package com.system.reservation.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class TeacherEntity {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long id;

  @Column(length=100, nullable=false)
  private String password;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String name;
  private String email;
  private String dept;
  private String position;

  @Builder
  public void Teacher(Long id, String password, String name, String email, String dept, String position){
    this.id = id;
    this.password = password;
    this.name = name;
    this.email = email;
    this.dept = dept;
    this.position = position;
  }

  public void loginTeacher(Long id, String password){
    this.id = id;
    this.password = password;
  }



}
