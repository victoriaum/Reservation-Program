package com.system.reservation.User;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@RequiredArgsConstructor
public class PasswordSecureTest {
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  void passwordEncode() {
    // given
    String rawPassword = "12345678";

    // when
    String encodedPassword = passwordEncoder.encode(rawPassword);

    // then
    assertAll(
        () -> assertNotEquals(rawPassword, encodedPassword),
        () -> assertTrue(passwordEncoder.matches(rawPassword, encodedPassword))
    );
  }
}