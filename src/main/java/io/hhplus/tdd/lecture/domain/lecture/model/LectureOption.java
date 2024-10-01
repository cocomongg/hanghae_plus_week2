package io.hhplus.tdd.lecture.domain.lecture.model;

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
    @Column(name = "id")
    private Long id;

    @Column(name = "lecture_id", nullable = false)
    private Long lectureId;

    @Column(name = "lecturer_id", nullable = false)
    private Long lecturerId;

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

    @Column(name = "current_apply_count")
    private int currentApplyCount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
