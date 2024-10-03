package io.hhplus.tdd.lecture.infrastructure.db.lecture.repository;

import io.hhplus.tdd.lecture.infrastructure.db.lecture.entity.LectureOption;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureOptionJpaRepository extends JpaRepository<LectureOption, Long> {
    List<LectureOption> findAllByLectureId(Long lectureId);
}
