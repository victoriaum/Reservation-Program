package com.system.reservation.domain;

import com.system.reservation.web.dto.SchedulerDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SchedulerRepository extends JpaRepository<Scheduler, Long> {

  @Query("SELECT s.schedule_no, s.schedule_date, s.schedule_start, s.schedule_end, s.schedule_attender, s.schedule_space "
       + "FROM Scheduler s WHERE s.teacher_id=:teacher_id AND s.schedule_date>=:formatDate")
  List<String> getTeacherSchedule(@Param("teacher_id") String teacher_id, @Param("formatDate") String formatDate);

  @Query("SELECT s.schedule_attender FROM Scheduler s WHERE s.schedule_no=:schedule_no")
  String checkAttenders(@Param("schedule_no") Long schedule_no);

  @Modifying
  @Query("UPDATE Scheduler s SET s.schedule_attender=:attenders WHERE s.schedule_no=:schedule_no")
  void updateReport(@Param("schedule_no") Long schedule_no, @Param("attenders") String attenders);

  @Query("SELECT s FROM Scheduler s WHERE s.teacher_id=:teacher_id AND s.schedule_date>=:formatDate")
  List<SchedulerDto> getSchedule(@Param("teacher_id") String teacher_id, @Param("formatDate") String formatDate);

}

