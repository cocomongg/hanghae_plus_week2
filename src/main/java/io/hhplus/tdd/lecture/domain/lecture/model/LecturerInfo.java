package io.hhplus.tdd.lecture.domain.lecture.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LecturerInfo {
    private Long lecturerId;
    private String name;
}
