package com.system.reservation.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ScheduleRepository extends JpaRepository<Student, String> {

  @Query("SELECT t FROM Schedule t WHERE t.teacher_id=:checkedTeacher")
  List<String> findByTeacher_id(@Param("checkedTeacher") String checkedTeacher);

}

