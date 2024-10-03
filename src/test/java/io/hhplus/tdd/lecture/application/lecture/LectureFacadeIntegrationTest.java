package io.hhplus.tdd.lecture.application.lecture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import io.hhplus.tdd.lecture.domain.lecture.model.LectureInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureOptionInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureWithOption;
import io.hhplus.tdd.lecture.infrastructure.db.entity.lecture.Lecture;
import io.hhplus.tdd.lecture.infrastructure.db.entity.lecture.LectureOption;
import io.hhplus.tdd.lecture.infrastructure.db.repository.lecture.LectureJpaRepository;
import io.hhplus.tdd.lecture.infrastructure.db.repository.lecture.LectureOptionJpaRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class LectureFacadeIntegrationTest {

    @Autowired
    private LectureJpaRepository lectureJpaRepository;

    @Autowired
    private LectureOptionJpaRepository lectureOptionJpaRepository;

    @Autowired
    private LectureFacade lectureFacade;

    @AfterEach
    public void tearDown() {
        lectureJpaRepository.deleteAllInBatch();
        lectureOptionJpaRepository.deleteAllInBatch();
    }

    @DisplayName("신청가능한 특강 목록 조회 테스트")
    @Nested
    class GetApplicableLecturesTest {
        @DisplayName("date에 신청 가능한 특강 목록을 반환한다.")
        @Test
        void should_ReturnLectureWithOptionList_When_FindByDate() {
            // given
            LocalDate date = LocalDate.now();
            LectureOption lectureOption1 = LectureOption.builder()
                .lectureOptionId(1L)
                .lectureId(1L)
                .applyStartDate(date)
                .applyEndDate(date.plusDays(1))
                .maxApplyCount(30)
                .currentApplyCount(0)
                .createdAt(LocalDateTime.now())
                .build();

            LectureOption lectureOption2 = LectureOption.builder()
                .lectureOptionId(2L)
                .lectureId(1L)
                .applyStartDate(date)
                .applyEndDate(date.plusDays(1))
                .maxApplyCount(30)
                .currentApplyCount(0)
                .createdAt(LocalDateTime.now())
                .build();

            Lecture lecture1 = Lecture.builder()
                .lectureId(1L)
                .title("title1")
                .description("desc1")
                .lecturerName("name1")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

            lectureJpaRepository.save(lecture1);
            lectureOptionJpaRepository.saveAll(List.of(lectureOption1, lectureOption2));

            // when
            List<LectureWithOption> lecturesWithOption = lectureFacade.getApplicableLectures(date);

            // then
            assertThat(lecturesWithOption).hasSize(1);
            LectureWithOption lectureWithOption = lecturesWithOption.get(0);

            LectureInfo lectureInfo = lectureWithOption.getLectureInfo();
            assertThat(lectureInfo.getLectureId()).isEqualTo(lecture1.getLectureId());
            assertThat(lectureInfo.getTitle()).isEqualTo(lecture1.getTitle());
            assertThat(lectureInfo.getDescription()).isEqualTo(lecture1.getDescription());
            assertThat(lectureInfo.getLecturerName()).isEqualTo(lecture1.getLecturerName());

            List<LectureOptionInfo> lectureOptionInfos = lectureWithOption.getLectureOptionInfos();
            assertThat(lectureOptionInfos).hasSize(2)
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
}