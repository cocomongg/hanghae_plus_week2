package io.hhplus.tdd.lecture.domain.lecture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;

import io.hhplus.tdd.lecture.domain.lecture.model.ApplyHistoryWithLecture;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureApplyHistoryInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureOptionInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureWithOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LectureMapperTest {

    private LectureMapper lectureMapper;

    @BeforeEach
    public void setup() {
        lectureMapper = new LectureMapper();
    }

    @DisplayName("Lecture목록과 LectureOption Map을 입력받아 LectureWithOption 목록으로 반환한다.")
    @Test
    void should_ReturnLectureWithOptionList_When_Convert () {
        // given
        LectureInfo lectureInfo1 = LectureInfo.builder()
            .lectureId(1L)
            .title("title")
            .description("desc")
            .lecturerName("name")
            .build();

        LocalDate nowDate = LocalDate.now();
        LocalDateTime dateTime = LocalDateTime.now();
        LectureOptionInfo lectureOptionInfo1 = LectureOptionInfo.builder()
            .lectureOptionId(1L)
            .lectureId(1L)
            .maxApplyCount(30)
            .currentApplyCount(0)
            .applyStartDate(nowDate)
            .applyEndDate(nowDate)
            .lectureStartAt(dateTime)
            .lectureEndAt(dateTime)
            .build();

        LectureOptionInfo lectureOptionInfo2 = LectureOptionInfo.builder()
            .lectureOptionId(2L)
            .lectureId(1L)
            .maxApplyCount(30)
            .currentApplyCount(0)
            .applyStartDate(nowDate)
            .applyEndDate(nowDate)
            .lectureStartAt(dateTime)
            .lectureEndAt(dateTime)
            .build();

        List<LectureInfo> lectures = List.of(lectureInfo1);
        Map<Long, List<LectureOptionInfo>> lectureOptionMap = Map.of(1L,
            List.of(lectureOptionInfo1, lectureOptionInfo2));

        // when
        List<LectureWithOption> lecturesWithOption = lectureMapper.toLecturesWithOption(lectures,
            lectureOptionMap);

        // then
        assertThat(lecturesWithOption).hasSize(1);

        LectureWithOption lectureWithOption = lecturesWithOption.get(0);
        assertThat(lectureWithOption.getLectureInfo()).isEqualTo(lectureInfo1);

        List<LectureOptionInfo> lectureOptions = lectureWithOption.getLectureOptionInfos();
        assertThat(lectureOptions).hasSize(2);

        assertThat(lectureOptions.get(0)).isEqualTo(lectureOptionInfo1);
        assertThat(lectureOptions.get(1)).isEqualTo(lectureOptionInfo2);
    }

    @DisplayName("LectureApplyHistory목록과 Lecture목록을 입력받아 ApplyHistoryWithLecture 목록으로 반환한다.")
    @Test
    void should_ReturnApplyHistoryWithLectureList_When_Convert() {
        // given
        LocalDateTime now = LocalDateTime.now();
        LectureApplyHistoryInfo applyHistoryInfo1 = LectureApplyHistoryInfo.builder()
            .lectureApplyHistoryId(1L)
            .memberId(1L)
            .lectureId(1L)
            .lectureOptionId(1L)
            .success(true)
            .appliedAt(now)
            .build();

        LectureApplyHistoryInfo applyHistoryInfo2 = LectureApplyHistoryInfo.builder()
            .lectureApplyHistoryId(2L)
            .memberId(1L)
            .lectureId(2L)
            .lectureOptionId(2L)
            .success(true)
            .appliedAt(now)
            .build();

        LectureInfo lectureInfo1 = LectureInfo.builder()
            .lectureId(1L)
            .title("title")
            .description("desc")
            .lecturerName("name")
            .build();

        LectureInfo lectureInfo2 = LectureInfo.builder()
            .lectureId(2L)
            .title("title")
            .description("desc")
            .lecturerName("name")
            .build();

        List<LectureApplyHistoryInfo> lectureApplyHistories = List.of(applyHistoryInfo1,
            applyHistoryInfo2);

        List<LectureInfo> lectures = List.of(lectureInfo1, lectureInfo2);

        // when
        List<ApplyHistoryWithLecture> result = lectureMapper.toApplyHistoriesWithLecture(
            lectureApplyHistories, lectures);

        // then
        assertThat(result)
            .hasSize(2)
            .extracting(ApplyHistoryWithLecture::getLectureApplyHistoryId,
                ApplyHistoryWithLecture::getLectureId, ApplyHistoryWithLecture::getLectureOptionId,
                ApplyHistoryWithLecture::getLectureTitle, ApplyHistoryWithLecture::getLectureDescription,
                ApplyHistoryWithLecture::getLecturerName, ApplyHistoryWithLecture::getAppliedAt)
            .containsExactlyInAnyOrder(
                tuple(
                    applyHistoryInfo1.getLectureApplyHistoryId(),
                    lectureInfo1.getLectureId(), applyHistoryInfo1.getLectureOptionId(),
                    lectureInfo1.getTitle(), lectureInfo1.getDescription(),
                    lectureInfo1.getLecturerName(), applyHistoryInfo1.getAppliedAt()),
                tuple(
                    applyHistoryInfo2.getLectureApplyHistoryId(),
                    lectureInfo2.getLectureId(), applyHistoryInfo2.getLectureOptionId(),
                    lectureInfo2.getTitle(), lectureInfo2.getDescription(),
                    lectureInfo2.getLecturerName(), applyHistoryInfo2.getAppliedAt()));
    }
}