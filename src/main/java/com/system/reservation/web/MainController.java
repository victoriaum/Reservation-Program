package com.system.reservation.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

/*  @Autowired
  private InterReportService service;*/

  @GetMapping("/")
  public String mainPage() {
    return "/WEB-INF/views/index.html";
  }





}
