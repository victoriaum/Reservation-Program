package com.system.reservation.web;

import com.system.reservation.service.ScheduleService;
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

  // 오늘 날짜 구하기
  LocalDate now = LocalDate.now();
  String todayDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
  String year = now.format(DateTimeFormatter.ofPattern("yyyy"));
  String month = now.format(DateTimeFormatter.ofPattern("MM"));
  String today = now.format(DateTimeFormatter.ofPattern("dd"));

  @RequestMapping("/schedule")
  public String schedule(HttpServletRequest request, Model m) {

    HttpSession httpSession = request.getSession();
    String loginType = (String)httpSession.getAttribute("loginType");
    m.addAttribute("loginType",loginType);
    return "schedule";
  }

  @RequestMapping("/getSchedule")
  public String getSchedule(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
                            HttpServletRequest request, Model m) {

    HttpSession httpSession = request.getSession();
    String loginType = (String)httpSession.getAttribute("loginType");

    if("1".equals(loginType)){  // 선생님이 로그인한 경우
      TeacherDto teacherDto = (TeacherDto)httpSession.getAttribute("loginUser");
      String teacher_id = teacherDto.getTeacher_id();
      List<SchedulerDto> scheduleList = scheduleService.getTeacherWeekSchedule(teacher_id, startDate, endDate);
      m.addAttribute("scheduleList",scheduleList);
    }
    else {  // 학생이 로그인한 경우
      StudentDto studentDto = (StudentDto)httpSession.getAttribute("loginUser");
      String student_id = studentDto.getStudent_id();
      List<String> scheduleList = scheduleService.getStudentWeekSchedule(student_id, startDate, endDate);
      m.addAttribute("scheduleList",scheduleList);
    }

    return "schedule";
  }


}