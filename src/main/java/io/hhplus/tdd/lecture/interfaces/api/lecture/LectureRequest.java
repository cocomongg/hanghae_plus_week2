package io.hhplus.tdd.lecture.interfaces.api.lecture;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class LectureRequest {

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetApplicableLectures {
        @NotNull
        private LocalDate date;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApplyLecture {
        @NotNull
        @Min(1)
        private Long memberId;

        @NotNull
        @Min(1)
        private Long lectureOptionId;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetAppliedLectureHistories {
        @NotNull
        @Min(1)
        private Long memberId;
    }

}
