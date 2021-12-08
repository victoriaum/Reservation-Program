package com.system.reservation.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

  @RequestMapping(value = {"/", "/index"})
  public String mainPage() {
    return "index";
  }

  @RequestMapping("/schedule")
  public String schedule() { return "schedule";  }

  @RequestMapping("/notice")
  public String notice() {
    return "notice";
  }

  @RequestMapping("/mypage")
  public String mypage() {
    return "mypage";
  }


}
