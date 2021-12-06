package com.system.reservation.web.dto;

import com.system.reservation.domain.Schedule;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ScheduleDto {
  private Long schedule_no;
  private String teacher_id;
  private String student_id;
  private String schedule_date;
  private String schedule_time;
  private Integer schedule_space;

  @Builder
  public ScheduleDto(Schedule entity){
    this.schedule_no = entity.getSchedule_no();
    this.teacher_id = entity.getTeacher_id();
    this.student_id = entity.getStudent_id();
    this.schedule_date = entity.getSchedule_date();
    this.schedule_time = entity.getSchedule_time();
    this.schedule_space = entity.getSchedule_space();
  }
}
