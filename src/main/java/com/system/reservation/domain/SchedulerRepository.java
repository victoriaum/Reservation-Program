package com.system.reservation.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SchedulerRepository extends JpaRepository<Scheduler, Long> {

  @Query("SELECT s.schedule_no, s.schedule_date, s.schedule_start, s.schedule_end, s.schedule_attender, s.schedule_space "
       + "FROM Scheduler s WHERE s.teacher_id=:checkedTeacher AND s.schedule_date>=:formatDate")
  List<String> getTeacherSchedule(@Param("checkedTeacher") String checkedTeacher, @Param("formatDate") String formatDate);

  @Query("SELECT s.schedule_attender FROM Scheduler s WHERE s.schedule_no=:schedule_no")
  String checkattenders(@Param("schedule_no") Long schedule_no);

  @Modifying
  @Query("UPDATE Scheduler s SET s.schedule_attender=:attenders WHERE s.schedule_no=:schedule_no")
  void scheduleRequest(@Param("schedule_no") Long schedule_no, @Param("attenders") String attenders);

}

