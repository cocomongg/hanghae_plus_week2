package io.hhplus.tdd.lecture.infrastructure.db.repository.lecture;

import io.hhplus.tdd.lecture.infrastructure.db.entity.lecture.LectureApplyHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureApplyHistoryJpaRepository extends JpaRepository<LectureApplyHistory, Long> {

    boolean existsByMemberIdAndLectureIdAndSuccessIsTrue(Long memberId, Long LectureId);

    List<LectureApplyHistory> findAllByMemberIdAndSuccessIsTrue(Long memberId);
}
