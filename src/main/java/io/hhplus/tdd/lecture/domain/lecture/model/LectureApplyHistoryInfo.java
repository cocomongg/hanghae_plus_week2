package io.hhplus.tdd.lecture.domain.lecture.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LectureApplyHistoryInfo {
    private final Long lectureApplyHistoryId;
    private final Long memberId;
    private final Long lectureId;
    private final ApplyStatus applyStatus;
    private final LocalDateTime appliedAt;
}
