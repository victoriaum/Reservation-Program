package com.system.reservation.web;

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
  public String getTeacherSchedule(@RequestParam("checkedDept") String checkedDept,
                                  @RequestParam("checkedTeacher") String checkedTeacher) {
    List<String> teacherList = teacherService.getTeacherSchedule(checkedDept, checkedTeacher);
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("teacherList", teacherList);
    return jsonObject.toString();
  }

}
