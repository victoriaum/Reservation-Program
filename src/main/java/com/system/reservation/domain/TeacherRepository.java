package com.system.reservation.domain;

import com.system.reservation.web.dto.TeacherDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeacherRepository extends JpaRepository<Teacher, String> {

  @Query("SELECT t FROM Teacher t WHERE t.teacher_id=:id and t.teacher_password=:password")
  List<TeacherDto> findByTeacher_idAndTeacher_password(@Param("id") String id, @Param("password") String password);

  @Query("SELECT t FROM Teacher t WHERE t.teacher_no=:no")
  TeacherDto findByTeacher_no(@Param("no") Long no);

  @Query("SELECT DISTINCT t.teacher_dept FROM Teacher t")
  List<String> getDept();

  @Query("SELECT t FROM Teacher t WHERE t.teacher_dept=:dept and t.teacher_id NOT LIKE 'admin'")
  List<TeacherDto> getTeacher(@Param("dept") String dept);

  @Query("SELECT t FROM Teacher t WHERE t.teacher_id=:id")
  TeacherDto findByTeacher_id(@Param("id") String id);

  @Query("SELECT t.teacher_name, t.teacher_position FROM Teacher t WHERE t.teacher_id=:teacher_id")
  String teacherInfo(@Param("teacher_id") String teacher_id);

  @Query("SELECT t.request_students FROM Teacher t WHERE t.teacher_id=:teacher_id")
  String requestCheck(@Param("teacher_id") String teacher_id);
}
