package com.system.reservation.domain;

import com.system.reservation.web.dto.TeacherDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeacherRepository extends JpaRepository<Teacher, String> {

  @Query("SELECT new com.system.reservation.web.dto.StudentDto"
      + "(t.teacher_id, t.teacher_name, t.teacher_name, t.teacher_dept, t.teacher_position) "
      + "FROM Teacher t WHERE t.teacher_id=:id and t.teacher_password=:password")
  List<TeacherDto> findByTeacher_idAndTeacher_password(@Param("id") String id, @Param("password") String password);

}
