package io.hhplus.tdd.lecture.domain.lecture.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LectureInfo {
    private Long lectureId;
    private String title;
    private String description;
    private String lecturerName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

