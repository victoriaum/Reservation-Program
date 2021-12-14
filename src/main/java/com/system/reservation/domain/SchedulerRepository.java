package com.system.reservation.domain;

import com.system.reservation.web.dto.SchedulerDto;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SchedulerRepository extends JpaRepository<Scheduler, Long> {

  @Query("SELECT s FROM Scheduler s WHERE s.teacher_id=:teacher_id AND s.schedule_date>=:formatDate")
  Collection<Scheduler> getTeacherSchedule(@Param("teacher_id") String teacher_id, @Param("formatDate") String formatDate);

  @Query("SELECT s.schedule_attender FROM Scheduler s WHERE s.schedule_no=:schedule_no")
  String checkAttenders(@Param("schedule_no") Long schedule_no);

  @Modifying
  @Query("UPDATE Scheduler s SET s.schedule_attender=:attenders WHERE s.schedule_no=:schedule_no")
  void updateReport(@Param("schedule_no") Long schedule_no, @Param("attenders") String attenders);

  @Query("SELECT s.schedule_no, s.schedule_date, s.schedule_start, s.schedule_end, s.schedule_attender, s.schedule_space "
      + "FROM Scheduler s WHERE s.schedule_no=:schedule_no")
  String findBySchedule_no(@Param("schedule_no") Long schedule_no);

  @Query("SELECT s FROM Scheduler s "
      + "WHERE s.teacher_id=:teacher_id AND s.schedule_date=:formatDate ORDER BY s.schedule_start ASC")
  Collection<Scheduler> teacherTodaySchedule(@Param("teacher_id") String teacher_id, @Param("formatDate") String formatDate);


  @Query("SELECT s.schedule_no, s.schedule_start, t.teacher_dept, t.teacher_name, t.teacher_position, s.teacher_id"
      + " FROM Scheduler s LEFT JOIN Teacher t"
      + " ON s.teacher_id = t.teacher_id"
      + " WHERE s.schedule_attender LIKE %:student_id% AND s.schedule_date=:formatDate"
      + " ORDER BY s.schedule_start ASC")
  List<String> studentTodaySchedule(@Param("student_id") String student_id, @Param("formatDate") String formatDate);


}

