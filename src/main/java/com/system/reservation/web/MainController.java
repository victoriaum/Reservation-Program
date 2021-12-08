package com.system.reservation.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

  @GetMapping("/")
  public String mainPage() {
    return "index";
  }

  @PostMapping("/schedule")
  public String schedule() { return "schedule";  }

  @PostMapping("/notice")
  public String notice() {
    return "notice";
  }

  @PostMapping("/mypage")
  public String mypage() {
    return "mypage";
  }


}
