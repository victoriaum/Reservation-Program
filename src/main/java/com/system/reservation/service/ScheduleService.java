package com.system.reservation.service;

import com.system.reservation.domain.SchedulerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ScheduleService {
  private final SchedulerRepository schedulerRepository;

  @Transactional
  public List<String> getTeacherSchedule(String checkedTeacher, String formatDate) {
    List<String> scheduleList = schedulerRepository.getTeacherSchedule(checkedTeacher, formatDate);
    return scheduleList;
  };

  @Transactional
  public Integer scheduleRequest(Long schedule_no, String login_id) {
    String attenders = schedulerRepository.checkattenders(schedule_no);

    if(attenders.contains(login_id)){ // 일정 내 참여자가 있는 경우
      return 0;
    } else {  // 일정 내 참여자가 없는 경우
      if(attenders.isEmpty()){
        attenders = login_id;
      } else {
        attenders = attenders + "," + login_id;
      }
      System.out.println(attenders);
      schedulerRepository.scheduleRequest(schedule_no, attenders);
      return 1;
    }


  }

}
