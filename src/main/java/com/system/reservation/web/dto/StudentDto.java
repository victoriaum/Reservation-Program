package com.system.reservation.web.dto;

import com.system.reservation.domain.Student;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StudentDto {
  private String student_id;
  private String student_password;
  private String student_name;
  private String student_email;
  private String student_grade;

  @Builder
  public StudentDto(Student entity){
    this.student_id = entity.getStudent_id();
    this.student_name = entity.getStudent_name();
    this.student_email = entity.getStudent_email();
    this.student_grade = entity.getStudent_grade();
  }
}
