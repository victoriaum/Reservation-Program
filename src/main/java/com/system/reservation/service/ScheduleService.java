package com.system.reservation.service;

import com.system.reservation.domain.SchedulerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ScheduleService {
  private final SchedulerRepository schedulerRepository;

  @Transactional
  public List<String> getTeacherSchedule(String checkedTeacher) {
    List<String> scheduleList = schedulerRepository.getTeacherSchedule(checkedTeacher);
    return scheduleList;
  };

}
