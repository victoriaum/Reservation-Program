package com.system.reservation.web.dto;

import com.system.reservation.domain.Schedule;
import com.system.reservation.domain.Teacher;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ScheduleDto {
  private Long schedule_no;
  private Teacher teacher_id;
  private String schedule_attender;
  private String schedule_date;
  private String schedule_startTime;
  private String schedule_endTime;
  private Integer schedule_space;

  @Builder
  public ScheduleDto(Schedule entity){
    this.schedule_no = entity.getSchedule_no();
    this.teacher_id = entity.getTeacher_id();
    this.schedule_attender = entity.getSchedule_attender();
    this.schedule_date = entity.getSchedule_date();
    this.schedule_startTime = entity.getSchedule_startTime();
    this.schedule_endTime = entity.getSchedule_endTime();
    this.schedule_space = entity.getSchedule_space();
  }
}
