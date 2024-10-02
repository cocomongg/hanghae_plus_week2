package io.hhplus.tdd.lecture.domain.lecture.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LectureCapacityInfo {
    private final Long lectureCapacityId;
    private final Long lectureOptionId;
    private final int currentApplyCount;
}
