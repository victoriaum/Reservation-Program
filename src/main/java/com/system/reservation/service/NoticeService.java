package com.system.reservation.service;

import com.system.reservation.domain.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class NoticeService {
  private final NoticeRepository noticeRepository;


}
