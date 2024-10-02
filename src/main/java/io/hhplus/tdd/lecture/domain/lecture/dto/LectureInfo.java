package io.hhplus.tdd.lecture.domain.lecture.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LectureInfo {
    private Long lectureId;
    private String title;
    private String description;

    private List<Long> lectureOptionIds;

    private Long lecturerId;

    private String lecturerName;

    private LocalDateTime createdAt;
}
