package com.system.reservation.web;

import com.system.reservation.service.StudentService;
import com.system.reservation.service.TeacherService;
import com.system.reservation.web.dto.StudentDto;
import com.system.reservation.web.dto.TeacherDto;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class UserController {
  private final TeacherService teacherService;
  private final StudentService studentService;

  @GetMapping("/login")
  public String loginPage(HttpServletRequest request) {
    HttpSession session = request.getSession();
    session.removeAttribute("loginUser");
    session.removeAttribute("type");
    return "login";
  }

  @PostMapping("/login")
  public String login(@RequestParam("inlineRadioOptions") String type, @RequestParam("id") String id,
                      @RequestParam("password") String password, Model m, HttpServletRequest request) {

    HttpSession httpSession = request.getSession();

    if("1".equals(type)){
      TeacherDto teacherDto = teacherService.findByTeacher_idAndTeacher_password(id, password);
      if(teacherDto==null){
        m.addAttribute("loginFailed","일치하는 회원이 없습니다. 다시 로그인해주세요!");
        return "login";
      }
      else {
        httpSession.setAttribute("loginUser",teacherDto);
        httpSession.setAttribute("loginType",type);
        return "index";
      }

    } else {
      StudentDto studentDto = studentService.findByStudent_idAndStudent_password(id, password);
      if(studentDto==null){
        m.addAttribute("loginFailed","일치하는 회원이 없습니다. 다시 로그인해주세요!");
        return "login";
      }
      else {
        httpSession.setAttribute("loginUser",studentDto);
        httpSession.setAttribute("loginType",type);
        return "index";
      }
    }



  }
}
