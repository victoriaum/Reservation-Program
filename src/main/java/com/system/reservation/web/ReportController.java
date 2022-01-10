package com.system.reservation.web;

import com.system.reservation.service.ScheduleService;
import com.system.reservation.service.TeacherService;
import com.system.reservation.web.dto.SchedulerDto;
import com.system.reservation.web.dto.TeacherDto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
public class ReportController {
  private final TeacherService teacherService;
  private final ScheduleService scheduleService;

  SchedulerDto schedulerDto = new SchedulerDto();

  // 오늘 날짜 구하기
  LocalDate now = LocalDate.now();
  DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  String today = now.format(dateTimeFormatter);


  @RequestMapping("/report_s")
  public String report_s(Model m) {
    List<String> deptList = teacherService.getDept();
    m.addAttribute("deptList",deptList);
    return "report_s";
  }

  @ResponseBody
  @RequestMapping("/getTeacher")
  public String getTeacher(@RequestParam("checkedDept") String checkedDept) {
    List<String> teacherList = teacherService.getTeacher(checkedDept);
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("teacherList", teacherList);
    return jsonObject.toString();
  }

  @ResponseBody
  @RequestMapping("/getTeacherSchedule")
  public Object getTeacherSchedule(@RequestParam("checkedTeacher") String teacher_id) {
    List<SchedulerDto> scheduleList = scheduleService.getTeacherSchedule(teacher_id, today);
    return scheduleList;
  }

  @ResponseBody
  @RequestMapping("/sendReport")
  public String sendReport(@RequestParam("schedule_no") Long schedule_no, @RequestParam("login_id") String login_id) {
    Integer result = scheduleService.sendReport(schedule_no, login_id);
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("result", result);
    return jsonObject.toString();
  }

  @ResponseBody
  @RequestMapping("/cancelReport")
  public String cancelReport(@RequestParam("schedule_no") Long schedule_no, @RequestParam("login_id") String login_id) {
    Integer result = scheduleService.cancelReport(schedule_no, login_id);
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("result", result);
    return jsonObject.toString();
  }


  @RequestMapping("/report_t")
  public String report_t(HttpServletRequest request, Model m) {
    HttpSession httpSession = request.getSession();
    TeacherDto teacherDto = (TeacherDto)httpSession.getAttribute("loginUser");
    String teacher_id = teacherDto.getTeacher_id();

    List<SchedulerDto> scheduleList = scheduleService.getTeacherSchedule(teacher_id, today);


    if(scheduleList.size()==0) {
      m.addAttribute("schedule","등록한 일정이 없습니다.");
    } else {
      m.addAttribute("schedule","");
      m.addAttribute("scheduleList",scheduleList);
    }

    return "report_t";
  }

  @RequestMapping("/report_t/makeSchedule")
  public String makeSchedule(@RequestParam("no") String no, HttpServletRequest request, Model m) {

    if(!"0".equals(no)){
      Long schedule_no = Long.parseLong(no);
      String schedule = scheduleService.findBySchedule_no(schedule_no);
      m.addAttribute("schedule", schedule);
    } else {
      m.addAttribute("no", no);
      m.addAttribute("schedule", "");
    }

    return "makeSchedule";
  }

  @RequestMapping("/report_t/saveSchedule")
  public String saveSchedule(@RequestParam("date") String date,
                             @RequestParam("startTime") String start,
                             @RequestParam("endTime") String end,
                             @RequestParam("space") String space,
                             HttpServletRequest request, Model m) {

    HttpSession httpSession = request.getSession();
    TeacherDto teacherDto = (TeacherDto)httpSession.getAttribute("loginUser");
    String teacher_id = teacherDto.getTeacher_id();

    schedulerDto.setTeacher_id(teacher_id);
    schedulerDto.setSchedule_attender("");
    schedulerDto.setSchedule_date(date);
    schedulerDto.setSchedule_start(start);
    schedulerDto.setSchedule_end(end);
    schedulerDto.setSchedule_space(space);

    scheduleService.saveSchedule(schedulerDto);

    List<SchedulerDto> scheduleList = scheduleService.getTeacherSchedule(teacher_id, today);
    m.addAttribute("scheduleList",scheduleList);

    return "report_t";
  }


  @RequestMapping("/report_t/editSchedule")
  public String editSchedule(@RequestParam("no") String no,
                              @RequestParam("date") String date,
                              @RequestParam("startTime") String start,
                              @RequestParam("endTime") String end,
                              @RequestParam("space") String space,
                              Model m) {

    Long schedule_no = Long.parseLong(no);
    scheduleService.editSchedule(schedule_no, date, start, end, space);
    m.addAttribute("","");

    return "redirect:/report_t";
  }


  @ResponseBody
  @RequestMapping("/report_t/deleteSchedule")
  public String deleteSchedule(@RequestParam("no") String schedule_no, Model m) {
    Long no = Long.parseLong(schedule_no);
    scheduleService.deleteById(no);
    return "성공적으로 삭제되었습니다.";
  }
}
