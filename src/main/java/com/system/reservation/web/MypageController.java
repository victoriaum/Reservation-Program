package com.system.reservation.web;

import com.system.reservation.service.NoticeService;
import com.system.reservation.web.dto.NoticeDto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class MypageController {

  @RequestMapping("/mypage")
  public String mypage() {
    return "mypage";
  }

}