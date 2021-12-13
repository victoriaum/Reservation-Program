package com.system.reservation.service;

import com.system.reservation.domain.SchedulerRepository;
import com.system.reservation.web.dto.SchedulerDto;
import java.util.ArrayList;
import java.util.Arrays;
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
  public List<String> getTeacherSchedule(String teacher_id, String formatDate) {
    List<String> scheduleList = schedulerRepository.getTeacherSchedule(teacher_id, formatDate);
    return scheduleList;
  };

  @Transactional
  public Integer sendReport(Long schedule_no, String login_id) {
    String attenders = schedulerRepository.checkAttenders(schedule_no);

    if(attenders.contains(login_id)){ // 일정 내 현재 요청한 참여자가 있는 경우
      return 0;

    } else {  // 일정 내 현재 요청한 참여자가 없는 경우
      if(attenders.isEmpty()){
        attenders = login_id;
      } else {
        attenders = attenders + "," + login_id;
      }
      schedulerRepository.updateReport(schedule_no, attenders);
      return 1;
    }
  }

  @Transactional
  public Integer cancelReport(Long schedule_no, String login_id) {
    String attenders = schedulerRepository.checkAttenders(schedule_no);

    if(attenders!=null && attenders.contains(login_id)){ // 일정 내 현재 요청한 참여자가 있는 경우
      if(attenders.contains(",")){
        String[] attendersArray = attenders.split(",");
        List<String> tempList = new ArrayList<String>(Arrays.asList(attendersArray));
        tempList.remove(login_id);
        attenders = tempList.toString();
        attenders = attenders.substring(1,attenders.length()-1);
      } else {
        attenders = "";
      }
      schedulerRepository.updateReport(schedule_no, attenders);
      return 1;

    } else {  // 일정 내 현재 요청한 참여자가 없는 경우
      return 0;
    }
  }

  @Transactional
  public String findBySchedule_no(Long schedule_no) {
    String schedule = schedulerRepository.findBySchedule_no(schedule_no);
    return schedule;
  }


}
