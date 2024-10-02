package io.hhplus.tdd.lecture.application.lecture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;

import io.hhplus.tdd.lecture.application.lecture.LectureDto.LectureItem;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LecturerInfo;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LectureMapperTest {

    private LectureMapper mapper;

    @BeforeEach
    public void setup() {
        this.mapper = new LectureMapper();
    }

    @DisplayName("입력받은 Lecture목록과 Leturer목록을 LectureItem 목록으로 변환한다.")
    @Test
    void should_ReturnLectureItemList_When_InputLecturesAndLecturers() {
        // given
        LocalDateTime now = LocalDateTime.now();
        LectureInfo lectureInfo1 = LectureInfo.builder()
            .lectureId(1L)
            .title("title1")
            .description("desc1")
            .lecturerId(1L)
            .createdAt(now)
            .build();

        LectureInfo lectureInfo2 = LectureInfo.builder()
            .lectureId(2L)
            .title("title2")
            .description("desc2")
            .lecturerId(2L)
            .createdAt(now)
            .build();

        LecturerInfo lecturerInfo1 = LecturerInfo.builder()
            .lecturerId(1L)
            .name("name1")
            .build();

        LecturerInfo lecturerInfo2 = LecturerInfo.builder()
            .lecturerId(2L)
            .name("name2")
            .build();

        List<LectureInfo> lectureInfos = List.of(lectureInfo1, lectureInfo2);
        List<LecturerInfo> lecturerInfos = List.of(lecturerInfo1, lecturerInfo2);

        // when
        List<LectureItem> lectures = mapper.toLectureItem(lectureInfos, lecturerInfos);

        // then
        assertThat(lectures)
            .hasSize(2)
            .extracting(LectureItem::getLectureId, LectureItem::getTitle, LectureItem::getDescription,
                LectureItem::getLecturerId, LectureItem::getLecturerName, LectureItem::getCreatedAt)
            .containsExactlyInAnyOrder(
                tuple(lectureInfo1.getLectureId(), lectureInfo1.getTitle(), lectureInfo1.getDescription(),
                    lecturerInfo1.getLecturerId(), lecturerInfo1.getName(), lectureInfo1.getCreatedAt()),
                tuple(lectureInfo2.getLectureId(), lectureInfo2.getTitle(), lectureInfo2.getDescription(),
                    lecturerInfo2.getLecturerId(), lecturerInfo2.getName(), lectureInfo2.getCreatedAt())
            );
    }
}