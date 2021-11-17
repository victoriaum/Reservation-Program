package com.example.reservation.controller;

import com.example.reservation.service.InterReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

/*  @Autowired
  private InterReportService service;*/

  @GetMapping("/")
  public String mainPage() {
    return "index";
  }





}
