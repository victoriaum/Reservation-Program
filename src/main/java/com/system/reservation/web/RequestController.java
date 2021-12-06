package com.system.reservation.web;

import com.system.reservation.service.TeacherService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

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

}
