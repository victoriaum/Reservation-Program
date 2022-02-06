package com.system.reservation.web;

import com.system.reservation.service.StudentService;
import com.system.reservation.service.TeacherService;
import com.system.reservation.web.dto.StudentDto;
import com.system.reservation.web.dto.TeacherDto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class AdminController {
  private final StudentService studentService;
  private final TeacherService teacherService;

  // 오늘 날짜 구하기
  LocalDate now = LocalDate.now();
  String todayDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
  String year = now.format(DateTimeFormatter.ofPattern("yyyy"));
  String month = now.format(DateTimeFormatter.ofPattern("MM"));
  String day = now.format(DateTimeFormatter.ofPattern("dd"));
  int dayCnt = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month),1).lengthOfMonth();


  @RequestMapping("/admin")
  public String admin(HttpServletRequest request, Model m) {
    return "admin";
  }

  @RequestMapping("/admin/peopleRegister")
  public String peopleRegister(HttpServletRequest request, Model m) {
    return "peopleRegister";
  }


  @ResponseBody
  @PostMapping("/admin/studentRegister")
  public String studentRegister(@RequestParam("idAll") String idAll,
      @RequestParam("nameAll") String nameAll, @RequestParam("gradeAll") String gradeAll) {
    String[] idArr = idAll.split(" ");
    String[] nameArr = nameAll.split(" ");
    String[] gradeArr = gradeAll.split(" ");
    String hasId = "";
    String result = "";

    // 아이디 존재 여부 확인
    for(int i=0; i<idArr.length; i++){
      int check = studentService.findByStudent_id(idArr[i]);

      if(1==check){   // 이미 아이디가 등록된 경우
        if(hasId.isEmpty()){
          hasId+=idArr[i];
        } else {
          hasId+=", "+idArr[i];
        }
      }
    }

    /*todo
    고민중인 로직 - 일부 확실치 않아도 옳은 것은 등록할 것인지..
    일단, 이미 등록된 경우 전체 저장하지 않기로 해놓음*/

    if(hasId.isEmpty()){
      for(int i=0; i<idArr.length; i++){
        StudentDto studentDto = new StudentDto();

        studentDto.setStudent_id(idArr[i]);
        studentDto.setStudent_password(idArr[i]);
        studentDto.setStudent_email(idArr[i]+"@cudh.com");
        studentDto.setStudent_grade(gradeArr[i]);
        studentDto.setStudent_name(nameArr[i]);

        studentService.studentRegister(studentDto);
      }
      result="1";
    } else {
      result=hasId;
    }

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("result", result);
    return jsonObject.toString();
  }


  @ResponseBody
  @PostMapping("/admin/teacherRegister")
  public String teacherRegister(@RequestParam("idAll") String idAll, @RequestParam("nameAll") String nameAll,
      @RequestParam("positionAll") String positionAll, @RequestParam("dept") String dept) {
    String[] idArr = idAll.split(" ");
    String[] nameArr = nameAll.split(" ");
    String[] positionArr = positionAll.split(" ");
    String hasId = "";
    String result = "";

    // 아이디 존재 여부 확인
    for(int i=0; i<idArr.length; i++){
      int check = teacherService.findByTeacher_id(idArr[i]);

      if(1==check){   // 이미 아이디가 등록된 경우
        if(hasId.isEmpty()){
          hasId+=idArr[i];
        } else {
          hasId+=", "+idArr[i];
        }
      }
    }

    if(hasId.isEmpty()){
      for(int i=0; i<idArr.length; i++){
        TeacherDto teacherDto = new TeacherDto();

        teacherDto.setTeacher_id(idArr[i]);
        teacherDto.setTeacher_password(idArr[i]);
        teacherDto.setTeacher_email(idArr[i]+"@cudh.com");
        teacherDto.setTeacher_dept(dept);
        teacherDto.setTeacher_name(nameArr[i]);
        teacherDto.setTeacher_position(positionArr[i]);

        teacherService.teacherRegister(teacherDto);
      }
      result="1";
    } else {
      result=hasId;
    }

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("result", result);
    return jsonObject.toString();
  }

}