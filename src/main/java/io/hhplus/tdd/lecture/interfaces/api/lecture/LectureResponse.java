package io.hhplus.tdd.lecture.interfaces.api.lecture;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class LectureResponse {

    @Builder
    @Getter
    public static class GetLectures {
        private Long lectureId;
        private String title;
        private String description;
        private List<Long> lectureOptionIds;
        private Long lecturerId;
        private String lecturerName;
        private LocalDateTime createdAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetApplicableLectures {
        private Long lectureOptionsId;
        private String lectureTitle;
        private String lecturerName;
        private int currentApplyCount;
        private int maxApplyCount;
        private LocalDateTime applyBeginAt;
        private LocalDateTime applyEndAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetLectureApplyHistories {
        private Long lectureId;
        private String lectureTitle;
        private String lecturerName;
        private LocalDateTime appliedAt;
    }
}
