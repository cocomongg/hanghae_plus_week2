package io.hhplus.tdd.lecture.infrastructure.db.repository.lecture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import io.hhplus.tdd.lecture.domain.lecture.LectureRepository;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureOptionInfo;
import io.hhplus.tdd.lecture.infrastructure.db.entity.lecture.Lecture;
import io.hhplus.tdd.lecture.infrastructure.db.entity.lecture.LectureOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LectureRepositoryImplTest {

    @Mock
    private LectureJpaRepository lectureJpaRepository;
    @Mock
    private LectureOptionJpaRepository lectureOptionJpaRepository;
    @InjectMocks
    private LectureRepositoryImpl lectureRepositoryImpl;

    @DisplayName("lectureId목록을 통해 해당하는 lecutre목록을 조회하고 LectureInfo로 변환하여 반환한다.")
    @Test
    void should_ReturnLectureInfoList_When_FindByLectureIds () {
        // given
        LocalDateTime now = LocalDateTime.now();
        List<Long> lectureIds = List.of(1L, 2L);
        Lecture lecture1 = Lecture.builder()
            .lectureId(1L)
            .title("title1")
            .description("desc1")
            .lecturerName("name1")
            .createdAt(now)
            .updatedAt(now)
            .build();

        Lecture lecture2 = Lecture.builder()
            .lectureId(2L)
            .title("title2")
            .description("desc2")
            .lecturerName("name2")
            .createdAt(now)
            .updatedAt(now)
            .build();

        when(lectureJpaRepository.findAllById(lectureIds))
            .thenReturn(List.of(lecture1, lecture2));

        // when
        List<LectureInfo> lectureInfos = lectureRepositoryImpl.getLectures(lectureIds);

        // then
        assertThat(lectureInfos)
            .hasSize(2)
            .extracting(LectureInfo::getLectureId, LectureInfo::getTitle,
                LectureInfo::getDescription, LectureInfo::getLecturerName)
            .containsExactlyInAnyOrder(
                tuple(lecture1.getLectureId(), lecture1.getTitle(), lecture1.getDescription(),
                    lecture1.getLecturerName()),
                tuple(lecture2.getLectureId(), lecture2.getTitle(), lecture2.getDescription(),
                    lecture2.getLecturerName())
            );
    }

    @DisplayName("date 조건에 해당하는 LectureOptions를 확인하고 LectureId를 key로 하는 Map으로 변환하여 반환한다.")
    @Test
    void should_ReturnLectureOptionMap_When_FindByDate() {
        // given
        LocalDate date = LocalDate.now();
        Long lectureId = 1L;

        LectureOption lectureOption1 = LectureOption.builder()
            .lectureOptionId(1L)
            .lectureId(lectureId)
            .applyStartDate(date)
            .applyEndDate(date.plusDays(1))
            .maxApplyCount(30)
            .currentApplyCount(0)
            .build();

        LectureOption lectureOption2 = LectureOption.builder()
            .lectureOptionId(2L)
            .lectureId(lectureId)
            .applyStartDate(date)
            .applyEndDate(date.plusDays(1))
            .maxApplyCount(30)
            .currentApplyCount(0)
            .build();

        when(lectureOptionJpaRepository.findAvailableOptions(date))
            .thenReturn(List.of(lectureOption1, lectureOption2));

        // when
        Map<Long, List<LectureOptionInfo>> lectureOptionMap =
            lectureRepositoryImpl.getLectureOptionMapByDate(date);

        // then
        assertThat(lectureOptionMap).hasSize(1);

        List<LectureOptionInfo> lectureOptionInfos = lectureOptionMap.get(lectureId);
        assertThat(lectureOptionInfos)
            .hasSize(2)
            .extracting(LectureOptionInfo::getLectureOptionId, LectureOptionInfo::getLectureId,
                LectureOptionInfo::getMaxApplyCount, LectureOptionInfo::getCurrentApplyCount,
                LectureOptionInfo::getApplyStartDate, LectureOptionInfo::getApplyEndDate)
            .containsExactlyInAnyOrder(
                tuple(lectureOption1.getLectureOptionId(), lectureOption1.getLectureId(),
                    lectureOption1.getMaxApplyCount(), lectureOption1.getCurrentApplyCount(),
                    lectureOption1.getApplyStartDate(), lectureOption1.getApplyEndDate()),
                tuple(lectureOption2.getLectureOptionId(), lectureOption2.getLectureId(),
                    lectureOption2.getMaxApplyCount(), lectureOption2.getCurrentApplyCount(),
                    lectureOption2.getApplyStartDate(), lectureOption2.getApplyEndDate())
            );
    }
}