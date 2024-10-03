package io.hhplus.tdd.lecture.infrastructure.db.lecture.entity;

import io.hhplus.tdd.lecture.domain.lecture.model.LectureCapacityInfo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
public class LectureCapacity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_capacity_id")
    private Long lectureCapacityId;

    @Column(name = "lecture_option_id", nullable = false)
    private Long lectureOptionId;

    @Column(name = "current_apply_count", nullable = false)
    private int currentApplyCount = 0;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public LectureCapacityInfo toLectureCapacityInfo() {
        return LectureCapacityInfo.builder()
            .lectureCapacityId(this.lectureCapacityId)
            .lectureOptionId(this.lectureOptionId)
            .currentApplyCount(this.currentApplyCount)
            .build();
    }
}
