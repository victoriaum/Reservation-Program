package com.system.reservation.service;

import com.system.reservation.domain.StudentRepository;
import com.system.reservation.web.dto.StudentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StudentService {
  private final StudentRepository studentRepository;
  @Transactional
  public StudentDto login(String id, String password) {
    StudentDto studentDto = studentRepository.login(id, password).get(0);
    if(studentDto==null){
      return null;
    } else {
      return studentRepository.login(id, password).get(0);
    }
  }
}
