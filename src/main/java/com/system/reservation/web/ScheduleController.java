package com.system.reservation.web;

import com.system.reservation.service.ScheduleService;
import com.system.reservation.service.StudentService;
import com.system.reservation.web.dto.SchedulerDto;
import com.system.reservation.web.dto.StudentDto;
import com.system.reservation.web.dto.TeacherDto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.util.ParameterMap;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class ScheduleController {
  private final ScheduleService scheduleService;
  private final StudentService studentService;


  @RequestMapping("/schedule")
  public String schedule(HttpServletRequest request, Model m) {
    HttpSession httpSession = request.getSession();
    String loginType = (String)httpSession.getAttribute("loginType");
    return "schedule";
  }

  @ResponseBody
  @RequestMapping("/getSchedule")
  public String schedule(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
      HttpServletRequest request) {

    HttpSession httpSession = request.getSession();
    String loginType = (String)httpSession.getAttribute("loginType");
    List<String> scheduleList;

    if("1".equals(loginType)){  // 선생님이 로그인한 경우
      TeacherDto teacherDto = (TeacherDto)httpSession.getAttribute("loginUser");
      String teacher_id = teacherDto.getTeacher_id();
      scheduleList = scheduleService.getTeacherWeekSchedule(teacher_id, startDate, endDate);
    }
    else {  // 학생이 로그인한 경우
      StudentDto studentDto = (StudentDto)httpSession.getAttribute("loginUser");
      String student_id = studentDto.getStudent_id();
      scheduleList = scheduleService.getStudentWeekSchedule(student_id, startDate, endDate);
    }

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("scheduleList", scheduleList);
    return jsonObject.toString();
  }

  @ResponseBody
  @RequestMapping("/getNameList")
  public String schedule(@RequestParam("attenders") String attenders, HttpServletRequest request) {

    HttpSession httpSession = request.getSession();
    String[] attenderList = attenders.split(",");
    String nameList ="";

    for(int i=0; i<attenderList.length; i++){
      if(i==0){
        nameList = studentService.getNameList(attenderList[i]);
      } else {
        nameList += ", "+studentService.getNameList(attenderList[i]);
      }
    }

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("nameList", nameList);
    return jsonObject.toString();
  }
}