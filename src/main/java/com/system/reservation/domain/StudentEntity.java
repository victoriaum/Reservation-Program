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
public class StudentEntity {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long id;

  @Column(length=100, nullable=false)
  private String password;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String name;
  private String email;
  private String grade;

  @Builder
  public void Student(Long id, String password, String name, String email, String grade){
    this.id = id;
    this.password = password;
    this.name = name;
    this.email = email;
    this.grade = grade;
  }

  public void loginStudent(Long id, String password){
    this.id = id;
    this.password = password;
  }



}
