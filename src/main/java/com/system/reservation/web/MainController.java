package com.system.reservation.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

  @RequestMapping(value = {"/", "/index.do"})
  public String mainPage() {
    return "index";
  }

  @RequestMapping("/schedule.do")
  public String schedule() { return "schedule";  }

  @RequestMapping("/notice.do")
  public String notice() {
    return "notice";
  }

  @RequestMapping("/mypage.do")
  public String mypage() {
    return "mypage";
  }


}
