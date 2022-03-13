package com.system.reservation.service;

import com.system.reservation.domain.StudentRepository;
import com.system.reservation.web.dto.StudentDto;
import com.system.reservation.web.dto.TeacherDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StudentService {
  private final StudentRepository studentRepository;

  @Transactional
  public StudentDto getOneStudentInfo(Long no) {
    return studentRepository.findByStudent_no(no);
  }

  @Transactional
  public StudentDto getOneStudentInfoById(String id) {
    return studentRepository.findByStudent_id(id);
  }

  @Transactional
  public StudentDto findByStudent_idAndStudent_password(String id, String password) {

    if(studentRepository.findByStudent_idAndStudent_password(id, password).size()==0){
      return null;
    } else {
      return studentRepository.findByStudent_idAndStudent_password(id, password).get(0);
    }

  }

  @Transactional
  public String getNameList(String id) {
    return studentRepository.getNameList(id);
  }

  @Transactional
  public StudentDto editPassword(String id, String password) {
    StudentDto studentDto = studentRepository.findByStudent_id(id);
    studentDto.setStudent_password(password);
    studentRepository.save(studentDto.toEntity());
    return studentDto;
  }

  @Transactional
  public int editAccount(StudentDto studentDto) {
    studentRepository.save(studentDto.toEntity());
    return 1;
  }

  @Transactional
  public Integer findByStudent_id(String id) {
    StudentDto studentDto = studentRepository.findByStudent_id(id);

    if(studentDto!=null){
      return 1;
    } else {
      return 0;
    }
  }

  @Transactional
  public void studentRegister(StudentDto studentDto) {
    studentRepository.save(studentDto.toEntity());
  }

  @Transactional
  public List<StudentDto> getStudentAll() {
    return studentRepository.findAll().stream()
        .map(StudentDto::new)
        .collect(Collectors.toList());
  }

  @Modifying
  @Transactional
  public int delAccount(Long no) {
    StudentDto studentDto = studentRepository.findByStudent_no(no);
    studentRepository.delete(studentDto.toEntity());
    return 1;
  }
}
