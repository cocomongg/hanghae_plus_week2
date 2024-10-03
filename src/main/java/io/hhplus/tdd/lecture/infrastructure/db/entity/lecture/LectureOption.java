package io.hhplus.tdd.lecture.infrastructure.db.entity.lecture;

import io.hhplus.tdd.lecture.domain.lecture.model.LectureOptionInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
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

    @Column(name = "apply_start_date", nullable = false)
    private LocalDate applyStartDate;

    private LocalDate applyEndDate;

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
            .applyStartDate(this.applyStartDate)
            .applyEndDate(this.applyEndDate)
            .maxApplyCount(this.maxApplyCount)
            .currentApplyCount(this.currentApplyCount)
            .build();
    }

    public void increaseCurrentApplyCount() {
        this.currentApplyCount++;
    }
}
