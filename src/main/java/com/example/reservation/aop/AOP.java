package com.example.reservation.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AOP {

  @Pointcut("@annotation(org.springframework.web.bind.annotation.Login)")
  public void Login(){}

  @Before("Login()")
  public void before(JoinPoint joinPoint){

  }


}
