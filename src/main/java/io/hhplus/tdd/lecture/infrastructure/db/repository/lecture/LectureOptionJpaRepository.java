package io.hhplus.tdd.lecture.infrastructure.db.repository.lecture;

import io.hhplus.tdd.lecture.infrastructure.db.entity.lecture.LectureOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureOptionJpaRepository extends JpaRepository<LectureOptionEntity, Long> {

}
