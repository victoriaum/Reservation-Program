package com.system.reservation.service;

import com.system.reservation.domain.NoticeRepository;
import com.system.reservation.web.dto.NoticeDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class NoticeService {
  private final NoticeRepository noticeRepository;

  public List<NoticeDto> noticeInfo(String loginType) {
    return noticeRepository.findByNotice_view(loginType).stream()
        .map(NoticeDto::new)
        .collect(Collectors.toList());
  }
}
