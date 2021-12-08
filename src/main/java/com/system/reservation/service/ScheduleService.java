package com.system.reservation.service;

import com.system.reservation.domain.SchedulerRepository;
import com.system.reservation.web.dto.SchedulerDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
  public Integer scheduleRequest(Long schedule_no) {
    Integer result = schedulerRepository.scheduleRequest(schedule_no);
    return result;
  }

}
