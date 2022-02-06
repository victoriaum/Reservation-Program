package com.system.reservation.web;

import com.system.reservation.web.dto.StudentDto;
import com.system.reservation.web.dto.TeacherDto;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
public class AdminController {

  // 오늘 날짜 구하기
  LocalDate now = LocalDate.now();
  String todayDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
  String year = now.format(DateTimeFormatter.ofPattern("yyyy"));
  String month = now.format(DateTimeFormatter.ofPattern("MM"));
  String day = now.format(DateTimeFormatter.ofPattern("dd"));
  int dayCnt = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month),1).lengthOfMonth();


  @RequestMapping("/admin")
  public String admin(HttpServletRequest request, Model m) {
    return "admin";
  }

  @RequestMapping("/admin/peopleRegister")
  public String peopleRegister(HttpServletRequest request, Model m) {
    return "peopleRegister";
  }

  @PostMapping("/admin/studentRegister")
  public String studentRegister(@RequestParam("idAll") String idAll,
      @RequestParam("nameAll") String nameAll, @RequestParam("gradeAll") String gradeAll) {
    String[] idArr = idAll.split(" ");
    String[] nameArr = nameAll.split(" ");
    String[] gradeArr = nameAll.split(" ");

    for(int i=0; i<idArr.length; i++){
      StudentDto studentDto = null;
      studentDto.setStudent_id(idArr[i]);
      studentDto.setStudent_password(idArr[i]);
      studentDto.setStudent_email(idArr[i]+"@ckdu.com");
      studentDto.setStudent_grade(gradeArr[i]);
      studentDto.setStudent_name(nameArr[i]);

      Integer result = StudentService.requestReport(studentDto);
    }

    return "peopleRegister";
  }

}