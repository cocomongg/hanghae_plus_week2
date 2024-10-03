package io.hhplus.tdd.lecture.infrastructure.db.repository.lecture;

import io.hhplus.tdd.lecture.infrastructure.db.entity.lecture.LectureApplyHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureApplyHistoryJpaRepository extends JpaRepository<LectureApplyHistoryEntity, Long> {

}
