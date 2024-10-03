package io.hhplus.tdd.lecture.interfaces.api.lecture;

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

}
