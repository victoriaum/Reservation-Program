package com.system.reservation.domain;

import com.system.reservation.web.dto.TeacherDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeacherRepository extends JpaRepository<Teacher, String> {

  @Query("SELECT t FROM Teacher t WHERE t.teacher_id=:id and t.teacher_password=:password")
  List<TeacherDto> findByTeacher_idAndTeacher_password(@Param("id") String id, @Param("password") String password);

  @Query("SELECT DISTINCT t.teacher_dept FROM Teacher t")
  List<String> getDept();

  @Query("SELECT t.teacher_name, t.teacher_position, t.teacher_id FROM Teacher t WHERE t.teacher_dept=:checkedDept")
  List<String> findByTeacher_dept(@Param("checkedDept") String checkedDept);

  @Modifying
  @Query("UPDATE Teacher t SET t.teacher_password=:password WHERE t.teacher_id=:id")
  void updatePassword(@Param("id") String id, @Param("password") String password);
}
