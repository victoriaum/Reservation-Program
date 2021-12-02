package com.system.reservation.service;

import com.system.reservation.domain.TeacherRepository;
import com.system.reservation.web.dto.TeacherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TeacherService {
  private final TeacherRepository teacherRepository;
  @Transactional
  public TeacherDto findByTeacher_idAndTeacher_password(String id, String password) {
    TeacherDto teacherDto;

    if(teacherRepository.findByTeacher_idAndTeacher_password(id, password).size()==0){
      return null;
    } else {
      return teacherRepository.findByTeacher_idAndTeacher_password(id, password).get(0);
    }
  }
}
