package com.system.reservation.domain;

import com.system.reservation.web.dto.SchedulerDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SchedulerRepository extends JpaRepository<Scheduler, Long> {

  @Query("SELECT s.schedule_date,s.schedule_start,s.schedule_end,s.schedule_end,s.schedule_attender "
       + "FROM Scheduler s WHERE s.teacher_id=:checkedTeacher")
  List<String> getTeacherSchedule(@Param("checkedTeacher") String checkedTeacher);

}

