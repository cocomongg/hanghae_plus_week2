package io.hhplus.tdd.lecture.domain.lecture.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

public class LectureCommand {
    @Getter
    @Builder
    public static class CreateApplyHistory {
        private final Long memberId;
        private final Long lectureOptionId;
        private final boolean isSuccess;
        private final LocalDateTime appliedAt;
    }
}
