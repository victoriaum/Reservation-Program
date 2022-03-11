package com.system.reservation.service;

import com.system.reservation.domain.TeacherRepository;
import com.system.reservation.web.dto.TeacherDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TeacherService {
  private final TeacherRepository teacherRepository;

  @Transactional
  public TeacherDto getOneTeacherInfo(Long no) {
    return teacherRepository.findByTeacher_no(no);
  }

  @Transactional
  public TeacherDto findByTeacher_idAndTeacher_password(String id, String password) {

    if(teacherRepository.findByTeacher_idAndTeacher_password(id, password).size()==0){
      return null;
    } else {
      return teacherRepository.findByTeacher_idAndTeacher_password(id, password).get(0);
    }
  }

  @Transactional
  public List<String> getDept(){
    List<String> deptList = teacherRepository.getDept();
    return deptList;
  };

  @Transactional
  public List<TeacherDto> getTeacher(String dept){
    List<TeacherDto> deptList = teacherRepository.getTeacher(dept);
    return deptList;
  }

  @Transactional
  public TeacherDto editPassword(String id, String password) {
    TeacherDto teacherDto = teacherRepository.findByTeacher_id(id);
    teacherDto.setTeacher_password(password);
    teacherRepository.save(teacherDto.toEntity());
    return teacherDto;
  }

  @Transactional
  public Integer openRequest(String id, String student_id) {
    TeacherDto teacherDto = teacherRepository.findByTeacher_id(id);
    String request_students = teacherDto.getRequest_students();

    if(request_students.isEmpty()){
      request_students = student_id;
    } else {
      request_students += "," + student_id;
    }

    teacherDto.setRequest_students(request_students);
    teacherRepository.save(teacherDto.toEntity());

    return 1;
  }


  @Transactional
  public Integer revokeOpenRequest(String id, String student_id) {
    TeacherDto teacherDto = teacherRepository.findByTeacher_id(id);
    String request_students = teacherDto.getRequest_students();
    String[] list = request_students.split(",");
    String new_request_students = "";
    for(int i=0; i<list.length; i++){
      if(!student_id.equals((list[i]))){
        new_request_students += list[i];
      }
    }
    teacherDto.setRequest_students(new_request_students);
    teacherRepository.save(teacherDto.toEntity());

    return 1;
  }

  @Transactional
  public Integer requestCnt(String id) {
    String students = teacherRepository.findByTeacher_id(id).getRequest_students();
    if(students==null || students.isEmpty()){
      return 0;
    } else {
      return students.split(",").length;
    }
  }

  @Transactional
  public void requestCntDelete(TeacherDto teacherDto) {
    teacherRepository.save(teacherDto.toEntity());
  }

  @Transactional
  public Integer findByTeacher_id(String id) {
    TeacherDto teacherDto = teacherRepository.findByTeacher_id(id);

    if(teacherDto!=null){
      return 1;
    } else {
      return 0;
    }
  }

  @Transactional
  public void teacherRegister(TeacherDto teacherDto) {
    teacherRepository.save(teacherDto.toEntity());
  }

  @Transactional
  public List<TeacherDto> getTeacherAll() {
    return teacherRepository.findAll().stream()
        .map(TeacherDto::new)
        .collect(Collectors.toList());
  }

  @Transactional
  public String teacherInfo(String teacher_id) {
    String teacherInfo = teacherRepository.teacherInfo(teacher_id);
    return teacherInfo;
  }

  @Transactional
  public String requestCheck(String teacher_id, String student_id) {
    String requestCheck = teacherRepository.requestCheck(teacher_id);
    String[] requesters = requestCheck.split(",");
    String check = "0";
    for(int i=0; i<requesters.length; i++){
      if(student_id.equals(requesters[i].trim())){
        check = "1";
        break;
      }
    }
    return check;
  }

  @Modifying
  @Transactional
  public int delAccount(Long no) {
    teacherRepository.deleteById(String.valueOf(no));
    return 1;
  }
}
