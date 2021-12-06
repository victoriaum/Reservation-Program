package com.system.reservation.service;

import com.system.reservation.domain.TeacherRepository;
import com.system.reservation.web.dto.TeacherDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TeacherService {
  private final TeacherRepository teacherRepository;

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
  public List<String> getTeacher(String checkedDept){
    List<String> teacherList = teacherRepository.findByTeacher_dept(checkedDept);
    return teacherList;
  };

  @Transactional
  public List<String> getTeacherSchedule(String checkedDept, String checkedTeacher) {
    List<String> teacherList = teacherRepository.findByTeacher_dept(checkedDept);
    return teacherList;
  };

}
