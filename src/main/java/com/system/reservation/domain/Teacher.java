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
public class Teacher {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private String teacher_id;

  @Column(columnDefinition = "TEXT", length=100, nullable = false)
  private String teacher_password;
  private String teacher_name;
  private String teacher_email;
  private String teacher_dept;
  private String teacher_position;

  @Builder
  public void Teacher(String teacher_id, String teacher_password, String teacher_name,
                      String teacher_email, String teacher_dept, String teacher_position){
    this.teacher_id = teacher_id;
    this.teacher_password = teacher_password;
    this.teacher_name = teacher_name;
    this.teacher_email = teacher_email;
    this.teacher_dept = teacher_dept;
    this.teacher_position = teacher_position;
  }


}