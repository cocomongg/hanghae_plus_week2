package io.hhplus.tdd.lecture.domain.lecture.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LectureOptionInfo {
    private Long lectureOptionId;
    private Long lectureId;
    private LectureStatus status;
    private LocalDateTime lectureStartAt;
    private LocalDateTime lectureEndAt;
    private LocalDateTime applyBeginAt;
    private LocalDateTime applyEndAt;
    private int maxApplyCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
