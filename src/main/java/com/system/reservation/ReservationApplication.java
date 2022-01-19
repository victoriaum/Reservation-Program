package com.system.reservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@EnableAspectJAutoProxy
@SpringBootApplication
public class ReservationApplication {

  public static void main(String[] args) {
    SpringApplication.run(ReservationApplication.class, args);
  }
}
