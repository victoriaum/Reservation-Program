package com.system.reservation.web;

import com.system.reservation.service.NoticeService;
import com.system.reservation.service.ScheduleService;
import com.system.reservation.web.dto.SchedulerDto;
import com.system.reservation.web.dto.StudentDto;
import com.system.reservation.web.dto.TeacherDto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class NoticeController {

  private final NoticeService noticeService;

  // 오늘 날짜 구하기
  LocalDate now = LocalDate.now();
  String todayDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
  String year = now.format(DateTimeFormatter.ofPattern("yyyy"));
  String month = now.format(DateTimeFormatter.ofPattern("MM"));
  String day = now.format(DateTimeFormatter.ofPattern("dd"));
  int dayCnt = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month),1).lengthOfMonth();


  @RequestMapping("/notice")
  public String notice() {
    return "notice";
  }

}