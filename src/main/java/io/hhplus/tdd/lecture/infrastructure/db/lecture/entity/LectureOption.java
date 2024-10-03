package io.hhplus.tdd.lecture.infrastructure.db.lecture.entity;

import io.hhplus.tdd.lecture.domain.lecture.model.LectureOptionInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureStatus;
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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LectureOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_option_id")
    private Long lectureOptionId;

    @Column(name = "lecture_id", nullable = false)
    private Long lectureId;

    @Enumerated(EnumType.STRING)
    @Column(name = "lecture_status", nullable = false)
    private LectureStatus status;

    @Column(name = "lecture_start_at", nullable = false)
    private LocalDateTime lectureStartAt;

    @Column(name = "lecture_end_at", nullable = false)
    private LocalDateTime lectureEndAt;

    @Column(name = "max_apply_count")
    private int maxApplyCount;

    @Column(name = "current_apply_count", nullable = false)
    private int currentApplyCount = 0;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public LectureOptionInfo toLectureOptionInfo() {
        return LectureOptionInfo.builder()
            .lectureOptionId(this.lectureOptionId)
            .lectureId(this.lectureId)
            .status(this.status)
            .lectureStartAt(this.lectureStartAt)
            .lectureEndAt(this.lectureEndAt)
            .maxApplyCount(this.maxApplyCount)
            .currentApplyCount(this.currentApplyCount)
            .createdAt(this.createdAt)
            .updatedAt(this.updatedAt)
            .build();
    }
}
