package io.hhplus.tdd.lecture.infrastructure.db.lecture.repository;

import io.hhplus.tdd.lecture.infrastructure.db.lecture.entity.Lecturer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LecturerJpaRepository extends JpaRepository<Lecturer, Long> {
}
