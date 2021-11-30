package com.system.reservation.service;

import com.system.reservation.domain.TeacherRepository;
import com.system.reservation.web.dto.StudentDto;
import com.system.reservation.web.dto.TeacherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TeacherService {
  private final TeacherRepository teacherRepository;
  @Transactional
  public TeacherDto login(String id, String password) {
    TeacherDto teacherDto = teacherRepository.login(id, password).get(0);
    if(teacherDto==null){
      return null;
    } else {
      return teacherRepository.login(id, password).get(0);
    }
  }
}
