package io.hhplus.tdd.lecture.domain.lecture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import io.hhplus.tdd.lecture.domain.lecture.model.LectureInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureOptionInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureWithOption;
import java.time.LocalDate;
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
    void should_ReturnLectureWithOptionList_When_Conver () {
        // given
        LectureInfo lectureInfo1 = LectureInfo.builder()
            .lectureId(1L)
            .title("title")
            .description("desc")
            .lecturerName("name")
            .build();

        LocalDate nowDate = LocalDate.now();
        LectureOptionInfo lectureOptionInfo1 = LectureOptionInfo.builder()
            .lectureOptionId(1L)
            .lectureId(1L)
            .maxApplyCount(30)
            .currentApplyCount(0)
            .applyEndDate(nowDate)
            .applyEndDate(nowDate)
            .build();

        LectureOptionInfo lectureOptionInfo2 = LectureOptionInfo.builder()
            .lectureOptionId(2L)
            .lectureId(1L)
            .maxApplyCount(30)
            .currentApplyCount(0)
            .applyEndDate(nowDate)
            .applyEndDate(nowDate)
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
}