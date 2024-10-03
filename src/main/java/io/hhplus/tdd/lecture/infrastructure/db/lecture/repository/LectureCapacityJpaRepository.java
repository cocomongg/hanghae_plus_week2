package io.hhplus.tdd.lecture.infrastructure.db.lecture.repository;

import io.hhplus.tdd.lecture.infrastructure.db.lecture.entity.LectureCapacity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureCapacityJpaRepository extends JpaRepository<LectureCapacity, Long> {
    List<LectureCapacity> findAllByLectureOptionIdIn(List<Long> lectureOptionIds);
}
