package com.system.reservation.domain;

import com.system.reservation.web.dto.TeacherDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {
  @Query("SELECT t FROM TeacherEntity t WHERE t.id=:id and t.password=:password")
  List<TeacherDto> login(@Param("id") String id, @Param("password") String password);

}
