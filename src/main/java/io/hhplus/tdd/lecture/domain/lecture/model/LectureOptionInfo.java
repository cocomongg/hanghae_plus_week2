package io.hhplus.tdd.lecture.domain.lecture.model;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LectureOptionInfo {
    private Long lectureOptionId;
    private Long lectureId;
    private int maxApplyCount;
    private int currentApplyCount;
    private LocalDate applyStartDate;
    private LocalDate applyEndDate;
}
