package io.hhplus.tdd.lecture.domain.lecture.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LectureOptionInfo {
    private Long lectureOptionId;
    private Long lectureId;
    private LectureStatus status;
    private LocalDateTime lectureStartAt;
    private LocalDateTime lectureEndAt;
    private int maxApplyCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public boolean isApplicable(int currentApplyCount) {
        return LectureStatus.APPLYING.equals(this.status)
            && this.maxApplyCount > currentApplyCount;
    }
}
