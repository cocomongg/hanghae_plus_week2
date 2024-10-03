package io.hhplus.tdd.lecture.infrastructure.db.lecture.entity;

import io.hhplus.tdd.lecture.domain.lecture.model.ApplyStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

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

    @Column(name = "lecture_option_id", nullable = false)
    private Long lectureOptionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "apply_status", nullable = false)
    private ApplyStatus applyStatus;

    @Column(name = "applied_at", nullable = false)
    private LocalDateTime appliedAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
