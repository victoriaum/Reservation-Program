package com.system.reservation.domain;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SchedulerRepository extends JpaRepository<Scheduler, Long> {

  @Query("SELECT s FROM Scheduler s WHERE s.teacher_id=:teacher_id AND s.schedule_date>=:today"
      + " ORDER BY s.schedule_start DESC")
  Collection<Scheduler> getTeacherSchedule(@Param("teacher_id") String teacher_id, @Param("today") String today);

  @Query("SELECT s FROM Scheduler s WHERE s.teacher_id=:teacher_id AND s.schedule_date=:today"
      + " ORDER BY s.schedule_start DESC")
  Collection<Scheduler> getTodayTeacherSchedule(@Param("teacher_id") String teacher_id, @Param("today") String today);

  @Query("SELECT s.schedule_no, s.schedule_start, t.teacher_dept, t.teacher_name, t.teacher_position"
      + " FROM Scheduler s LEFT JOIN Teacher t"
      + " ON s.teacher_id = t.teacher_id"
      + " WHERE s.schedule_attender LIKE CONCAT('%',:student_id,'%') AND s.schedule_date=:today"
      + " ORDER BY s.schedule_start DESC")
  List<String> getTodayStudentSchedule(@Param("student_id") String student_id, @Param("today") String today);



  @Query("SELECT s.schedule_attender FROM Scheduler s WHERE s.schedule_no=:schedule_no")
  String checkAttenders(@Param("schedule_no") Long schedule_no);

  @Modifying
  @Query("UPDATE Scheduler s SET s.schedule_attender=:attenders WHERE s.schedule_no=:schedule_no")
  void updateReport(@Param("schedule_no") Long schedule_no, @Param("attenders") String attenders);

  @Query("SELECT s.schedule_no, s.schedule_date, s.schedule_start, s.schedule_end, s.schedule_attender, s.schedule_space "
      + "FROM Scheduler s WHERE s.schedule_no=:schedule_no")
  String findBySchedule_no(@Param("schedule_no") Long schedule_no);

    @Modifying
  @Query("UPDATE Scheduler s SET s.schedule_date=:date, s.schedule_start=:start, s.schedule_end=:end, s.schedule_space=:space WHERE s.schedule_no=:no")
  void editSchedule(@Param("no") Long no, @Param("date") String date, @Param("start") String start, @Param("end") String end, @Param("space") String space);
}

