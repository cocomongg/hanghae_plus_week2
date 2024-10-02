package io.hhplus.tdd.lecture.interfaces.api.lecture;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class LectureRequest {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApplyLecture {
        private Long memberId;
        private Long lectureOptionId;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetApplicableLectures {
        private Long memberId;
        private Long lectureId;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetLectureApplyHistories {
        private Long memberId;
    }
}
