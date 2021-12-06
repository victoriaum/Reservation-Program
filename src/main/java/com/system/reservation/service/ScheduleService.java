package com.system.reservation.service;

import com.system.reservation.domain.ScheduleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ScheduleService {
  private final ScheduleRepository scheduleRepository;

  @Transactional
  public List<String> getTeacherSchedule(String checkedTeacher) {
    List<String> scheduleList = scheduleRepository.findByTeacher_id(checkedTeacher);
    return scheduleList;
  };

}
