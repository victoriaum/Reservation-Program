package com.system.reservation.web;

import com.system.reservation.service.ScheduleService;
import com.system.reservation.service.StudentService;
import com.system.reservation.web.dto.StudentDto;
import com.system.reservation.web.dto.TeacherDto;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class ScheduleController {

  private final ScheduleService scheduleService;
  private final StudentService studentService;


  @RequestMapping("schedule")
  public String schedule(HttpServletRequest request, Model m) {
    HttpSession httpSession = request.getSession();
    String type = (String) httpSession.getAttribute("loginType");
    return "schedule";
  }

  @ResponseBody
  @RequestMapping("getSchedule")
  public String getSchedule(@RequestParam("startDate") String startDate,
      @RequestParam("endDate") String endDate, HttpServletRequest request) {

    HttpSession httpSession = request.getSession();
    String type = (String) httpSession.getAttribute("loginType");
    List<String> scheduleList = new ArrayList<>();
    JSONObject jsonObject = new JSONObject();

    if ("1".equals(type)) {  // 선생님이 로그인한 경우
      TeacherDto teacherDto = (TeacherDto) httpSession.getAttribute("loginUser");
      String teacher_id = teacherDto.getTeacher_id();
      scheduleList = scheduleService.getTeacherWeekSchedule(teacher_id, startDate, endDate);
      jsonObject.put("scheduleList", scheduleList);
    }
    else if("2".equals(type)) {  // 학생이 로그인한 경우
      StudentDto studentDto = (StudentDto) httpSession.getAttribute("loginUser");
      String student_id = studentDto.getStudent_id();
      // 참석자에 있는지 확인 후, scheduleList에 추가
      List<String> attenderList = scheduleService.getAttenderList(startDate,endDate);
      for(int i=0; i<attenderList.size(); i++){
        String[] checkpoint = attenderList.get(i).split(",");
        for(int j=1; j<checkpoint.length; j++){
          if(student_id.equals(checkpoint[j].trim())){
            String schedule = scheduleService.getSchedule(Long.parseLong(checkpoint[0]));
            scheduleList.add(scheduleList.size(),schedule);
          }
        }
      }
      jsonObject.put("scheduleList", scheduleList);
    }
    return jsonObject.toString();
  }

  @ResponseBody
  @RequestMapping("getNameList")
  public String getNameList(@RequestParam("attenders") String attenders,
      HttpServletRequest request) {

    HttpSession httpSession = request.getSession();
    String[] attenderList = attenders.split(",");
    String name, nameList = "";

    for (int i = 0; i < attenderList.length; i++) {
      name = attenderList[i].trim();
      if (i == 0) {
        nameList = studentService.getNameList(name);
      } else {
        nameList += ", " + studentService.getNameList(name);
      }
    }

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("nameList", nameList);
    return jsonObject.toString();
  }


  @ResponseBody
  @RequestMapping("requestCancel")
  public String requestCancel(@RequestParam("schedule_no") Long schedule_no,
      HttpServletRequest request) {

    HttpSession httpSession = request.getSession();
    StudentDto studentDto = (StudentDto) httpSession.getAttribute("loginUser");
    String login_id = studentDto.getStudent_id();

    scheduleService.cancelReport(schedule_no, login_id);

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("result", "취소성공");
    return jsonObject.toString();
  }
}