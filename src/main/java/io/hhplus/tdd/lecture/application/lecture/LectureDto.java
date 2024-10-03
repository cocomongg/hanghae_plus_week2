package io.hhplus.tdd.lecture.application.lecture;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

public class LectureDto {

    @Getter
    @Builder
    public static class LectureItem {
        private Long lectureId;
        private String title;
        private String description;
        private Long lecturerId;
        private String lecturerName;
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    public static class LectureOptionItem {
        private Long lectureId;
        private Long lectureOptionId;
        private int currentApplyCount;
        private int maxApplyCount;
        private LocalDateTime lectureStartAt;
        private LocalDateTime lectureEndAt;
    }
}
