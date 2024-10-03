package io.hhplus.tdd.lecture.domain.lecture.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LectureApplyHistoryInfo {
    private Long lectureApplyHistoryId;
    private Long memberId;
    private Long lectureId;
    private Long lectureOptionId;
    private boolean success;
    private LocalDateTime appliedAt;
}
