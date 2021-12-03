package com.system.reservation.web.dto;

import com.system.reservation.domain.Teacher;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TeacherDto {
  private String teacher_id;
  private String teacher_password;
  private String teacher_name;
  private String teacher_email;
  private String teacher_dept;
  private String teacher_position;

  @Builder
  public TeacherDto(Teacher entity){
    this.teacher_id = entity.getTeacher_id();
    this.teacher_password = entity.getTeacher_password();
    this.teacher_name = entity.getTeacher_name();
    this.teacher_email = entity.getTeacher_email();
    this.teacher_dept = entity.getTeacher_dept();
    this.teacher_position = entity.getTeacher_position();
  }
}
