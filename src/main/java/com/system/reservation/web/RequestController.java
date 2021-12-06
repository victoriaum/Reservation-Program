package com.system.reservation.web;

import com.system.reservation.service.ScheduleService;
import com.system.reservation.service.TeacherService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class RequestController {
  private final TeacherService teacherService;
  private final ScheduleService scheduleService;

  @PostMapping("/request")
  public String request(Model m) {
    List<String> deptList = teacherService.getDept();
    m.addAttribute("deptList",deptList);
    return "request";
  }

  @ResponseBody
  @PostMapping("/getTeacher")
  public String getTeacher(@RequestParam("checkedDept") String checkedDept) {
    List<String> teacherList = teacherService.getTeacher(checkedDept);
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("teacherList", teacherList);
    return jsonObject.toString();
  }

  @ResponseBody
  @PostMapping("/getTeacherSchedule")
  public String getTeacherSchedule(@RequestParam("checkedTeacher") String checkedTeacher) {
    List<String> scheduleList = scheduleService.getTeacherSchedule(checkedTeacher);
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("scheduleList", scheduleList);
    return jsonObject.toString();
  }

}
