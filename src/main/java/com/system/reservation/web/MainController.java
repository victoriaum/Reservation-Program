package com.system.reservation.web;

import com.system.reservation.domain.SchedulerRepository;
import com.system.reservation.service.ScheduleService;
import com.system.reservation.web.dto.SchedulerDto;
import com.system.reservation.web.dto.StudentDto;
import com.system.reservation.web.dto.TeacherDto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class MainController {
  private final ScheduleService scheduleService;

  // 오늘 날짜 구하기
  LocalDate now = LocalDate.now();
  DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  String formatDate = now.format(dateTimeFormatter);


  @RequestMapping(value = {"/", "/index"})
  public String mainPage(HttpServletRequest request, Model m) {
    HttpSession httpSession = request.getSession();
    String loginType = (String)httpSession.getAttribute("loginType");

    if("1".equals(loginType)){  // 선생님이 로그인한 경우
      TeacherDto teacherDto = (TeacherDto)httpSession.getAttribute("loginUser");
      String teacher_id = teacherDto.getTeacher_id();
      List<SchedulerDto> scheduleList = scheduleService.teacherTodaySchedule(teacher_id, formatDate);
      m.addAttribute("scheduleList",scheduleList);
    }
    else {  // 학생이 로그인한 경우
      StudentDto studentDto = (StudentDto)httpSession.getAttribute("loginUser");
      String student_id = studentDto.getStudent_id();
      List<String> scheduleList = scheduleService.studentTodaySchedule(student_id, formatDate);
      m.addAttribute("scheduleList",scheduleList);
    }

    return "index";
  }

  @RequestMapping("/notice")
  public String notice() {
    return "notice";
  }

  @RequestMapping("/mypage")
  public String mypage() {
    return "mypage";
  }

  @RequestMapping("/test")
  public String test() {
    return "datepicker";
  }
}
