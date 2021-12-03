package com.system.reservation.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

  @GetMapping("/")
  public String mainPage() {
    return "index";
  }

  @GetMapping("/request")
  public String request() {
    return "request";
  }

  @GetMapping("/schedule")
  public String schedule() {
    return "schedule";
  }

  @GetMapping("/notice")
  public String notice() {
    return "notice";
  }

  @GetMapping("/mypage")
  public String mypage() {
    return "mypage";
  }

}
