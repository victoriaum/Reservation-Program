package com.system.reservation.domain;

import javax.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@NoArgsConstructor
@Entity
public class Scheduler{

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long schedule_no;

  @ManyToOne
  @JoinColumn(name = "teacher_id")
  private Teacher teacher;

  @Column(columnDefinition = "TEXT", length=600)
  private String schedule_attender;

  @Column(columnDefinition = "TEXT", length=100, nullable = false)
  private String schedule_date;
  private String schedule_startTime;
  private String schedule_endTime;

  @Column(columnDefinition = "TEXT", length=100)
  @ColumnDefault("6")
  private Integer schedule_space;

  @Builder
  public void Scheduler(String schedule_attender, String schedule_date, String schedule_startTime, String schedule_endTime, Integer schedule_space){
    this.schedule_attender = schedule_attender;
    this.schedule_date = schedule_date;
    this.schedule_startTime = schedule_startTime;
    this.schedule_endTime = schedule_endTime;
    this.schedule_space = schedule_space;
  }





}
