package com.system.reservation.web;

import com.system.reservation.service.StudentService;
import com.system.reservation.service.TeacherService;
import com.system.reservation.web.dto.StudentDto;
import com.system.reservation.web.dto.TeacherDto;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class MypageController {
  private final TeacherService teacherService;
  private final StudentService studentService;

  @RequestMapping("/mypage")
  public String mypage() {
    return "mypage";
  }

  @RequestMapping("/mypage/editAccount")
  public String editAccount() {
    return "editAccount";
  }

  @PostMapping("/report_t/editPassword")
  public String login(@RequestParam("id") String id, @RequestParam("password") String password, @RequestParam("type") String type,
                      HttpServletRequest request, HttpServletResponse response, Model m) throws IOException {

    HttpSession httpSession = request.getSession();

    if("1".equals(type)) {
      teacherService.updatePassword(id, password);
      TeacherDto teacherDto = teacherService.findByTeacher_idAndTeacher_password(id, password);
      httpSession.setAttribute("loginUser",teacherDto);
    } else {
      studentService.updatePassword(id, password);
      StudentDto studentDto = studentService.findByStudent_idAndStudent_password(id, password);
      httpSession.setAttribute("loginUser",studentDto);
    }

    return "mypage";
  }

}