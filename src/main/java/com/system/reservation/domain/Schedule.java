package com.system.reservation.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@NoArgsConstructor
@Entity
public class Schedule {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long schedule_no;

  @Column(columnDefinition = "TEXT", length=100, nullable = false)
  private String teacher_id;

  @Column(columnDefinition = "TEXT", length=100)
  private String student_id;

  @Column(columnDefinition = "TEXT", length=100, nullable = false)
  private String schedule_date;
  private String schedule_time;

  @Column(columnDefinition = "TEXT", length=100)
  @ColumnDefault("6")
  private Integer schedule_space;

  @Builder
  public void Schedule(Long schedule_no, String teacher_id, String student_id,
                      String schedule_date, String schedule_time, Integer schedule_space){
    this.schedule_no = schedule_no;
    this.teacher_id = teacher_id;
    this.student_id = student_id;
    this.schedule_date = schedule_date;
    this.schedule_time = schedule_time;
    this.schedule_space = schedule_space;
  }





}
