package io.hhplus.tdd.lecture.infrastructure.db.entity.lecture;

import io.hhplus.tdd.lecture.domain.lecture.model.LectureApplyHistoryInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LectureApplyHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_apply_history_id", nullable = false)
    private Long lectureApplyHistoryId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "lecture_id", nullable = false)
    private Long lectureId;

    @Column(name = "lecture_option_id", nullable = false)
    private Long lectureOptionId;

    @Column(name = "success", nullable = false)
    private boolean success;

    @Column(name = "applied_at", nullable = false)
    private LocalDateTime appliedAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public LectureApplyHistoryInfo toLectureApplyHistoryInfo() {
        return LectureApplyHistoryInfo.builder()
            .lectureApplyHistoryId(this.lectureApplyHistoryId)
            .memberId(this.memberId)
            .lectureId(this.lectureId)
            .lectureOptionId(this.lectureOptionId)
            .success(this.success)
            .appliedAt(this.appliedAt)
            .build();
    }
}
