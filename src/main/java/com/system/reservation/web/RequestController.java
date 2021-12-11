package com.system.reservation.web;

import com.system.reservation.service.ScheduleService;
import com.system.reservation.service.TeacherService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class RequestController {
  private final TeacherService teacherService;
  private final ScheduleService scheduleService;

  @RequestMapping("/request_s")
  public String request_s(Model m) {
    List<String> deptList = teacherService.getDept();
    m.addAttribute("deptList",deptList);
    return "request_s";
  }

  @RequestMapping("/request_t")
  public String request_t(Model m) {
    List<String> deptList = teacherService.getDept();
    m.addAttribute("deptList",deptList);
    return "request_t";
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
  public String getTeacherSchedule(@RequestParam("checkedTeacher") String checkedTeacher) {
    LocalDate now = LocalDate.now();
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    String formatDate = now.format(dateTimeFormatter);

    List<String> scheduleList = scheduleService.getTeacherSchedule(checkedTeacher, formatDate);
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("scheduleList", scheduleList);
    return jsonObject.toString();
  }

  @ResponseBody
  @RequestMapping("/sendRequest")
  public String sendRequest(@RequestParam("schedule_no") Long schedule_no, @RequestParam("login_id") String login_id) {
    Integer result = scheduleService.sendRequest(schedule_no, login_id);
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("result", result);
    return jsonObject.toString();
  }

  @ResponseBody
  @RequestMapping("/cancelRequest")
  public String cancelRequest(@RequestParam("schedule_no") Long schedule_no, @RequestParam("login_id") String login_id) {
    Integer result = scheduleService.cancelRequest(schedule_no, login_id);
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("result", result);
    return jsonObject.toString();
  }

}
