package com.system.reservation.domain;

import com.system.reservation.web.dto.StudentDto;
import com.system.reservation.web.dto.TeacherDto;
import java.util.List;
import org.codehaus.groovy.vmplugin.v8.PluginDefaultGroovyMethods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, String> {
  @Query("SELECT s FROM Student s WHERE s.student_id=:id and s.student_password=:password")
  List<StudentDto> findByStudent_idAndStudent_password(@Param("id") String id, @Param("password") String password);

  @Query("SELECT s FROM Student s WHERE s.student_no=:no")
  StudentDto findByStudent_no(@Param("no") Long no);

  @Query("SELECT s.student_name FROM Student s WHERE s.student_id=:id")
  String getNameList(@Param("id") String id);

  @Query("SELECT s FROM Student s WHERE s.student_id=:id")
  StudentDto findByStudent_id(@Param("id") String id);

}

