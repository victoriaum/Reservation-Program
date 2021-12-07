package com.system.reservation.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SchedulerRepository extends JpaRepository<Student, String> {

  @Query("SELECT s FROM Scheduler s WHERE s.teacher.teacher_id=:checkedTeacher")
  List<String> findByTeacher_id(@Param("checkedTeacher") String checkedTeacher);

}

