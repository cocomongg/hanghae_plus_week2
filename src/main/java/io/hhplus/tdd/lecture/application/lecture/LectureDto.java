package io.hhplus.tdd.lecture.application.lecture;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

public class LectureDto {

    @Getter
    @Builder
    public static class LectureItem {
        private Long lectureId;
        private Long lectureTitle;
        private Long lectureDescription;
        private Long lecturerName;
        private List<LectureOptionItem> lectureOptions;
    }

    @Getter
    @Builder
    public static class LectureOptionItem {
        private Long lectureOptionId;
        private int currentApplyCount;
        private int maxApplyCount;
        private LocalDateTime applyStartAt;
        private LocalDateTime applyEndAt;
    }

}
