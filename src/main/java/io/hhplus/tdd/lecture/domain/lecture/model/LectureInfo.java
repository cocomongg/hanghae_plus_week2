package io.hhplus.tdd.lecture.domain.lecture.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LectureInfo {
    private Long lectureId;
    private String title;
    private String description;
    private Long lecturerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
