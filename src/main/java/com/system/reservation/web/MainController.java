package com.system.reservation.web;

import com.system.reservation.service.ScheduleService;
import com.system.reservation.web.dto.StudentDto;
import com.system.reservation.web.dto.TeacherDto;
import java.beans.PropertyVetoException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class MainController {
  private final ScheduleService scheduleService;

  @Value("${spring.datasource.driver-class-name}")
  private String driver;

  @Value("${spring.datasource.url}")
  private String url;

  @Value("${spring.datasource.username}")
  private String username;

  @Value("${spring.datasource.password}")
  private String password;

  public DataSource dataSource() throws PropertyVetoException{
    final DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(driver);
    dataSource.setUrl(url);
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    return dataSource;
  }


  // 오늘 날짜 구하기
  LocalDate now = LocalDate.now();
  DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  String today = now.format(dateTimeFormatter);


  @RequestMapping(value = {"/", "/index"})
  public String mainPage() { return "index";  }

  @ResponseBody
  @RequestMapping(value = {"/getTodaySchedule"})
  public String getTodaySchedule(HttpServletRequest request, Model m) {
    HttpSession httpSession = request.getSession();
    String type = (String)httpSession.getAttribute("loginType");
    List<String> scheduleList;
    JSONObject jsonObject = new JSONObject();

    if("1".equals(type)){  // 선생님이 로그인한 경우
      TeacherDto teacherDto = (TeacherDto)httpSession.getAttribute("loginUser");
      String teacher_id = teacherDto.getTeacher_id();
      scheduleList = scheduleService.getTodayTeacherSchedule(teacher_id, today);
      jsonObject.put("scheduleList", scheduleList);
    }
    else if("2".equals(type)) {  // 학생이 로그인한 경우
      StudentDto studentDto = (StudentDto)httpSession.getAttribute("loginUser");
      String student_id = studentDto.getStudent_id();
      scheduleList = scheduleService.getTodayStudentSchedule(student_id, today);
      jsonObject.put("scheduleList", scheduleList);
    }

    return jsonObject.toString();
  }



}
