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
  public StudentDto findByStudent_idAndStudent_password(String id, String password) {

    if(studentRepository.findByStudent_idAndStudent_password(id, password).size()==0){
      return null;
    } else {
      return studentRepository.findByStudent_idAndStudent_password(id, password).get(0);
    }

  }
}
