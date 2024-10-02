package io.hhplus.tdd.lecture.infrastructure.db.lecture.entity;

import io.hhplus.tdd.lecture.domain.lecture.model.LectureStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
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

    @Column(name = "lecture_start_at")
    private LocalDateTime lectureStartAt;

    @Column(name = "lecture_end_at")
    private LocalDateTime lectureEndAt;

    @Column(name = "apply_start_at")
    private LocalDateTime applyBeginAt;

    @Column(name = "apply_end_at")
    private LocalDateTime applyEndAt;

    @Column(name = "max_apply_count")
    private int maxApplyCount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
