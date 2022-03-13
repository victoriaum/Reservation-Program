package com.system.reservation.web.dto;

import com.system.reservation.domain.Student;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class StudentDto {
  private Long student_no;
  private String student_id;
  private String student_password;
  private String student_name;
  private String student_grade;

  public StudentDto(Student entity){
    this.student_no = entity.getStudent_no();
    this.student_id = entity.getStudent_id();
    this.student_password = entity.getStudent_password();
    this.student_name = entity.getStudent_name();
    this.student_grade = entity.getStudent_grade();
  }

  public Student toEntity(){
    return Student.builder()
        .student_no(student_no)
        .student_id(student_id)
        .student_password(student_password)
        .student_name(student_name)
        .student_grade(student_grade)
        .build();
  }
}
