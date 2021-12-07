package com.system.reservation.web.dto;

import com.system.reservation.domain.Scheduler;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SchedulerDto {
  private Long schedule_no;
  private String teacher_id;
  private String schedule_attender;
  private String schedule_date;
  private String schedule_start;
  private String schedule_end;
  private Integer schedule_space;

  @Builder
  public SchedulerDto(Scheduler entity){
    this.schedule_no = entity.getSchedule_no();
    this.teacher_id = entity.getTeacher_id();
    this.schedule_attender = entity.getSchedule_attender();
    this.schedule_date = entity.getSchedule_date();
    this.schedule_start = entity.getSchedule_start();
    this.schedule_end = entity.getSchedule_end();
    this.schedule_space = entity.getSchedule_space();
  }
}
