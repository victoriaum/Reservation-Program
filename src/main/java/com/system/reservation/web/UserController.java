package com.system.reservation.web;

import com.system.reservation.service.StudentService;
import com.system.reservation.service.TeacherService;
import com.system.reservation.web.dto.StudentDto;
import com.system.reservation.web.dto.TeacherDto;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class UserController {
  private final TeacherService teacherService;
  private final StudentService studentService;
  private final PasswordEncoder passwordEncoder;


  @GetMapping("login")
  public String loginPage(HttpServletRequest request, Model m) throws UnsupportedEncodingException {
    HttpSession session = request.getSession();
    session.removeAttribute("loginUser");
    session.removeAttribute("loginType");

    return "login";
  }

  @PostMapping("login")
  public String login(@RequestParam("inlineRadioOptions") String type, @RequestParam("id") String id,
                      @RequestParam("password") String password, Model m,
                      HttpServletRequest request, HttpServletResponse response) throws IOException {
    HttpSession httpSession = request.getSession();

    if("1".equals(type)) {  // 교수로 로그인 하고자 하는 경우
      TeacherDto teacherDto = teacherService.getOneTeacherInfoById(id);
      String originPassword = teacherDto.getTeacher_password();

      if(teacherDto==null){
        m.addAttribute("loginFailed","아이디가 일치하는 회원이 없습니다!");
        return "login";
      }
      else {
        if(passwordEncoder.matches(password, originPassword)){
          httpSession.setAttribute("loginUser",teacherDto);
          if("admin".equals(id)){ // 관리자인 경우
            httpSession.setAttribute("loginType","3");
            response.sendRedirect("admin");
            return "admin";
          } else {  // 교수인 경우
            httpSession.setAttribute("loginType",type);
            response.sendRedirect("index");
            return "index";
          }
        }
        else {
          m.addAttribute("loginFailed","다른 비밀번호를 입력해주세요!");
          return "login";
        }
      }

    } else {  // 학생으로 로그인 하고자 하는 경우
      StudentDto studentDto = studentService.getOneStudentInfoById(id);
      String originPassword = studentDto.getStudent_password();

      if (studentDto == null) {
        m.addAttribute("loginFailed", "아이디가 일치하는 회원이 없습니다!");
        return "login";
      } else {
        if (passwordEncoder.matches(password, originPassword)) {
          httpSession.setAttribute("loginUser", studentDto);
          httpSession.setAttribute("loginType", type);
          response.sendRedirect("index");
          return "index";
        } else {
          m.addAttribute("loginFailed", "다른 비밀번호를 입력해주세요!");
          return "login";
        }
      }
    }
  }
}
