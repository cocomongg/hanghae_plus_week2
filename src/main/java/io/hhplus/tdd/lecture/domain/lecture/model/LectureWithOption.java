package io.hhplus.tdd.lecture.domain.lecture.model;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LectureWithOption {
    private final LectureInfo lectureInfo;
    private final List<LectureOptionInfo> lectureOptionInfos;
}
