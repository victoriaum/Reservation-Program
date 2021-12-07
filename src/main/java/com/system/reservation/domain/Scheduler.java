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

/*
  @ManyToOne
  @JoinColumn(name = "teacher_id")
*/

  @Column(columnDefinition = "TEXT", length=600)
  private String schedule_attender;

  @Column(columnDefinition = "TEXT", length=100, nullable = false)
  private String teacher_id;
  private String schedule_date;
  private String schedule_start;
  private String schedule_end;

  @Column(columnDefinition = "TEXT", length=100)
  @ColumnDefault("6")
  private Integer schedule_space;

  @Builder
  public void Scheduler(Long schedule_no, String schedule_attender, String teacher_id, String schedule_date, String schedule_start,
                        String schedule_end, Integer schedule_space){
    this.schedule_no = schedule_no;
    this.schedule_attender = schedule_attender;
    this.teacher_id = teacher_id;
    this.schedule_date = schedule_date;
    this.schedule_start = schedule_start;
    this.schedule_end = schedule_end;
    this.schedule_space = schedule_space;
  }





}
