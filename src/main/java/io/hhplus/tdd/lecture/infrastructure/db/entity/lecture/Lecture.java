package io.hhplus.tdd.lecture.infrastructure.db.entity.lecture;

import io.hhplus.tdd.lecture.domain.lecture.model.LectureInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Long lectureId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "lecturer_name", nullable = false)
    private String lecturerName;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public LectureInfo toLectureInfo() {
        return LectureInfo.builder()
            .lectureId(this.lectureId)
            .title(this.title)
            .description(this.description)
            .lecturerName(this.lecturerName)
            .createdAt(this.createdAt)
            .updatedAt(this.updatedAt)
            .build();
    }
}
