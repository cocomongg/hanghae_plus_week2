package io.hhplus.tdd.lecture.infrastructure.db.lecture.repository;

import io.hhplus.tdd.lecture.infrastructure.db.lecture.entity.LectureApplyHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureApplyHistoryJpaRepository extends JpaRepository<LectureApplyHistory, Long> {

}
