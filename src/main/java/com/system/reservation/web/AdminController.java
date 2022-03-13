package com.system.reservation.web;

import com.system.reservation.service.StudentService;
import com.system.reservation.service.TeacherService;
import com.system.reservation.web.dto.StudentDto;
import com.system.reservation.web.dto.TeacherDto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class AdminController {
  private final StudentService studentService;
  private final TeacherService teacherService;
  private final PasswordEncoder passwordEncoder;


  // 오늘 날짜 구하기
  LocalDate now = LocalDate.now();
  String todayDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
  String year = now.format(DateTimeFormatter.ofPattern("yyyy"));
  String month = now.format(DateTimeFormatter.ofPattern("MM"));
  String day = now.format(DateTimeFormatter.ofPattern("dd"));
  int dayCnt = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month),1).lengthOfMonth();


  @RequestMapping("admin")
  public String admin(HttpServletRequest request, Model m) {
    return "admin";
  }

  @RequestMapping("admin/adminPeopleRegister")
  public String peopleRegister(HttpServletRequest request, Model m) { return "adminPeopleRegister";  }

  @RequestMapping("admin/scheduleRegister")
  public String scheduleRegister(HttpServletRequest request, Model m) {
    return "scheduleRegister";
  }

  @RequestMapping("admin/adminPeopleManage")
  public String peopleManage(HttpServletRequest request, Model m) {
    return "adminPeopleManage";
  }

  @RequestMapping("admin/adminEditAccount")
  public String editAccount(@RequestParam("type") String type, @RequestParam("no") Long no, Model m) {
    if("1".equals(type)) {
      TeacherDto teacherDto = teacherService.getOneTeacherInfo(no);
      m.addAttribute(teacherDto);

    } else if("2".equals(type)) {
      StudentDto studentDto = studentService.getOneStudentInfo(no);
      m.addAttribute(studentDto);
    }
    m.addAttribute("type",type);
    return "adminEditAccount";
  }

  @RequestMapping("admin/deptManage")
  public String deptManage(HttpServletRequest request, Model m) {
    return "deptManage";
  }

  @RequestMapping("admin/scheduleManage")
  public String scheduleManage(HttpServletRequest request, Model m) {
    return "scheduleManage";
  }


  @ResponseBody
  @PostMapping("admin/studentRegister")
  public String studentRegister(@RequestParam("idAll") String idAll,
      @RequestParam("nameAll") String nameAll, @RequestParam("gradeAll") String gradeAll) {
    String[] idArr = idAll.split(" ");
    String[] nameArr = nameAll.split(" ");
    String[] gradeArr = gradeAll.split(" ");
    String hasId = "";
    String result = "";

    // 아이디 존재 여부 확인
    for(int i=0; i<idArr.length; i++){
      int check = studentService.findByStudent_id(idArr[i]);

      if(1==check){   // 이미 아이디가 등록된 경우
        if(hasId.isEmpty()){
          hasId+=idArr[i];
        } else {
          hasId+=", "+idArr[i];
        }
      }
    }

    if(hasId.isEmpty()){
      for(int i=0; i<idArr.length; i++){
        StudentDto studentDto = new StudentDto();

        studentDto.setStudent_id(idArr[i]);
        studentDto.setStudent_password(passwordEncoder.encode(idArr[i]));
        studentDto.setStudent_grade(gradeArr[i]);
        studentDto.setStudent_name(nameArr[i]);

        studentService.studentRegister(studentDto);
      }
      result="1";
    } else {
      result=hasId;
    }

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("result", result);
    return jsonObject.toString();
  }


  @ResponseBody
  @PostMapping("admin/teacherRegister")
  public String teacherRegister(@RequestParam("idAll") String idAll, @RequestParam("nameAll") String nameAll,
      @RequestParam("positionAll") String positionAll, @RequestParam("dept") String dept)
      throws Exception {
    String[] idArr = idAll.split(" ");
    String[] nameArr = nameAll.split(" ");
    String[] positionArr = positionAll.split(" ");
    String hasId = "";
    String result = "";

    // 아이디 존재 여부 확인
    for(int i=0; i<idArr.length; i++){
      int check = teacherService.findByTeacher_id(idArr[i]);

      if(1==check){   // 이미 아이디가 등록된 경우
        if(hasId.isEmpty()){
          hasId+=idArr[i];
        } else {
          hasId+=", "+idArr[i];
        }
      }
    }

    if(hasId.isEmpty()){  // 아이디가 없는 경우, 신규 등록
      for(int i=0; i<idArr.length; i++){
        TeacherDto teacherDto = new TeacherDto();

        teacherDto.setTeacher_id(idArr[i]);
        teacherDto.setTeacher_password(passwordEncoder.encode(idArr[i]));
        teacherDto.setTeacher_dept(dept);
        teacherDto.setTeacher_name(nameArr[i]);
        teacherDto.setTeacher_position(positionArr[i]);

        teacherService.teacherRegister(teacherDto);
      }
      result="1";
    } else {
      result=hasId;
    }

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("result", result);
    return jsonObject.toString();
  }


  @ResponseBody
  @PostMapping("admin/getStudentAll")
  public String getStudentAll(){
    List<StudentDto> studentDtoList = studentService.getStudentAll();
    String html = "";

    for(int i=0; i<studentDtoList.size(); i++){
      html += "<tbody><tr id='"+studentDtoList.get(i).getStudent_no()+"' onclick='func_info(this.id)'>"
          + "<td>"+studentDtoList.get(i).getStudent_grade()+"</td>"
          + "<td>"+studentDtoList.get(i).getStudent_id()+"</td>"
          + "<td>"+studentDtoList.get(i).getStudent_name()+"</td>"
          + "</tr></tbody>";
    }

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("html", html);
    return jsonObject.toString();
  }


  @ResponseBody
  @PostMapping("admin/getTeacherAll")
  public String getTeacherAll(){
    List<TeacherDto> teacherDtoList = teacherService.getTeacherAll();
    String html = "";

    for(int i=0; i<teacherDtoList.size(); i++){
      html += "<tbody><tr id='"+teacherDtoList.get(i).getTeacher_no()+"' onclick='func_info(this.id)'>"
          + "<td>"+teacherDtoList.get(i).getTeacher_dept()+"</td>"
          + "<td>"+teacherDtoList.get(i).getTeacher_id()+"</td>"
          + "<td>"+teacherDtoList.get(i).getTeacher_name()+"</td>"
          + "<td>"+teacherDtoList.get(i).getTeacher_position()+"</td>"
          + "</tr></tbody>";
    }

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("html", html);
    return jsonObject.toString();
  }


  @ResponseBody
  @PostMapping("admin/editAccount")
  public String editAccount(@RequestParam("type") String type, @RequestParam("info") String info){
    int result = 0;
    String[] infoArray = info.split(" ");
    String id = ((String[])infoArray)[0];
    String encodedPassword = passwordEncoder.encode(((String[])infoArray)[1]);
    String name = ((String[])infoArray)[2];

    if("1".equals(type)){
      TeacherDto teacherDto = teacherService.getOneTeacherInfoById(id);
      teacherDto.setTeacher_id(id);
      teacherDto.setTeacher_password(encodedPassword);
      teacherDto.setTeacher_name(name);
      teacherDto.setTeacher_dept(((String[])infoArray)[3]);
      teacherDto.setTeacher_position(((String[])infoArray)[4]);
      result = teacherService.editAccount(teacherDto);
    }
    else if("2".equals(type)){
      StudentDto studentDto = studentService.getOneStudentInfoById(id);
      studentDto.setStudent_id(id);
      studentDto.setStudent_password(encodedPassword);
      studentDto.setStudent_name(name);
      studentDto.setStudent_grade(((String[])infoArray)[3]);
      result = studentService.editAccount(studentDto);
    }

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("result", result);
    return jsonObject.toString();
  }



  @ResponseBody
  @PostMapping("admin/delAccount")
  public String delAccount(@RequestParam("type") String type, @RequestParam("no") Long no){
    int result = 0;

    if("1".equals(type)){
      result = teacherService.delAccount(no);
    }
    else if("2".equals(type)){
      result = studentService.delAccount(no);
    }

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("result", result);
    return jsonObject.toString();
  }

}