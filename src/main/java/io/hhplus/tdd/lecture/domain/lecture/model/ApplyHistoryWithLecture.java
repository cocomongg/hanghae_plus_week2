package io.hhplus.tdd.lecture.domain.lecture.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApplyHistoryWithLecture {
    private final Long lectureApplyHistoryId;
    private final Long lectureId;
    private final Long lectureOptionId;
    private final String lectureTitle;
    private final String lectureDescription;
    private final String lecturerName;
    private final LocalDateTime appliedAt;
}
