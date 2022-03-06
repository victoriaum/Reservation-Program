package com.system.reservation.web;

import com.system.reservation.service.StudentService;
import com.system.reservation.service.TeacherService;
import com.system.reservation.web.dto.StudentDto;
import com.system.reservation.web.dto.TeacherDto;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
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
  private final PasswordEncoder passwordEncoder;

  @RequestMapping("mypage")
  public String mypage() {
    return "mypage";
  }

  @RequestMapping("mypage/editAccount")
  public String editAccount() {
    return "editPassword";
  }

  @PostMapping("mypage/editPassword")
  public String login(@RequestParam("id") String id, @RequestParam("password") String password, @RequestParam("type") String type,
                      HttpServletRequest request, Model m) {

    HttpSession httpSession = request.getSession();
    String encodedPassword = passwordEncoder.encode(password);

    if("1".equals(type)) {
      TeacherDto teacherDto = teacherService.editPassword(id,encodedPassword);
      httpSession.setAttribute("loginUser",teacherDto);
    }
    else if("2".equals(type)) {
      StudentDto studentDto = studentService.editPassword(id,encodedPassword);
      httpSession.setAttribute("loginUser",studentDto);
    }

    m.addAttribute("result","변경성공");
    return "editPassword";
  }

}