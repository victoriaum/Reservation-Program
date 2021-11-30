package com.system.reservation.domain;

import com.system.reservation.web.dto.StudentDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
  @Query("SELECT s FROM StudentEntity s WHERE s.id=:id and s.password=:password")
  List<StudentDto> login(@Param("id") String id, @Param("password") String password);
}
