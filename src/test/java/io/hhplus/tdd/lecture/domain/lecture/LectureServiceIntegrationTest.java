package io.hhplus.tdd.lecture.domain.lecture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import io.hhplus.tdd.lecture.domain.lecture.model.LectureApplyHistoryInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureCommand.CreateApplyHistory;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureOptionInfo;
import io.hhplus.tdd.lecture.infrastructure.db.entity.lecture.Lecture;
import io.hhplus.tdd.lecture.infrastructure.db.entity.lecture.LectureApplyHistory;
import io.hhplus.tdd.lecture.infrastructure.db.entity.lecture.LectureOption;
import io.hhplus.tdd.lecture.infrastructure.db.repository.lecture.LectureApplyHistoryJpaRepository;
import io.hhplus.tdd.lecture.infrastructure.db.repository.lecture.LectureJpaRepository;
import io.hhplus.tdd.lecture.infrastructure.db.repository.lecture.LectureOptionJpaRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LectureServiceIntegrationTest {

    @Autowired
    private LectureJpaRepository lectureJpaRepository;

    @Autowired
    private LectureOptionJpaRepository lectureOptionJpaRepository;

    @Autowired
    private LectureApplyHistoryJpaRepository lectureApplyHistoryJpaRepository;

    @Autowired
    private LectureService lectureService;

    @BeforeEach
    public void setup() {
        lectureJpaRepository.deleteAllInBatch();
        lectureApplyHistoryJpaRepository.deleteAllInBatch();
        lectureOptionJpaRepository.deleteAllInBatch();
    }

    @DisplayName("LectureId에 해당하는 Lecture를 조회한다.")
    @Test
    void should_ReturnLectureInfo_When_FindByLectureId() {
        // given
        Lecture lecture = lectureJpaRepository.save(
            Lecture.builder()
                .title("title1")
                .description("desc1")
                .lecturerName("name1")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());

        // when
        LectureInfo lectureInfo = lectureService.getLecture(lecture.getLectureId());

        // then
        assertThat(lectureInfo.getLectureId()).isEqualTo(lecture.getLectureId());
        assertThat(lectureInfo.getTitle()).isEqualTo(lecture.getTitle());
        assertThat(lectureInfo.getDescription()).isEqualTo(lecture.getDescription());
        assertThat(lectureInfo.getLecturerName()).isEqualTo(lecture.getLecturerName());
        assertThat(lectureInfo.getCreatedAt()).isEqualTo(lecture.getCreatedAt());
        assertThat(lectureInfo.getUpdatedAt()).isEqualTo(lecture.getUpdatedAt());
    }

    @DisplayName("LectureId 목록에 해당하는 Lecute목록을 조회한다.")
    @Test
    void should_ReturnLectureInfoList_When_FindByLectureIds() {
        // given
        Lecture lecture1 = lectureJpaRepository.save(
            Lecture.builder()
                .title("title1")
                .description("desc1")
                .lecturerName("name1")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());

        Lecture lecture2 = lectureJpaRepository.save(
            Lecture.builder()
                .title("title2")
                .description("desc2")
                .lecturerName("name2")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());

        List<Long> lectureIds = List.of(lecture1.getLectureId(), lecture2.getLectureId());

        // when
        List<LectureInfo> lectures = lectureService.getLectures(lectureIds);

        // then
        assertThat(lectures).hasSize(2)
            .extracting(
                LectureInfo::getLectureId,
                LectureInfo::getTitle,
                LectureInfo::getDescription,
                LectureInfo::getLecturerName)
            .containsExactlyInAnyOrder(
                tuple(
                    lecture1.getLectureId(),
                    lecture1.getTitle(),
                    lecture1.getDescription(),
                    lecture1.getLecturerName()),
                tuple(
                    lecture2.getLectureId(),
                    lecture2.getTitle(),
                    lecture2.getDescription(),
                    lecture2.getLecturerName()));
    }

    @DisplayName("date 조건을 통해 LectureId를 key로 가지는 LectureOptionMap을 조회한다.")
    @Test
    void should_ReturnLectureOptionMap_When_FindByDate() {
        // given
        LocalDate date = LocalDate.now();
        LocalDateTime dateTime = LocalDateTime.now();
        Lecture lecture = lectureJpaRepository.save(
            Lecture.builder()
                .title("title1")
                .description("desc1")
                .lecturerName("name1")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());

        LectureOption lectureOption1 = lectureOptionJpaRepository.save(
            LectureOption.builder()
                .lectureId(lecture.getLectureId())
                .applyStartDate(date.minusDays(1))
                .applyEndDate(date.plusDays(1))
                .lectureStartAt(dateTime)
                .lectureEndAt(dateTime.plusHours(1))
                .maxApplyCount(30)
                .currentApplyCount(0)
                .createdAt(LocalDateTime.now())
                .build());

        LectureOption lectureOption2 = lectureOptionJpaRepository.save(
            LectureOption.builder()
                .lectureId(lecture.getLectureId())
                .applyStartDate(date.minusDays(1))
                .applyEndDate(date.plusDays(1))
                .lectureStartAt(dateTime)
                .lectureEndAt(dateTime.plusHours(1))
                .maxApplyCount(30)
                .currentApplyCount(0)
                .createdAt(LocalDateTime.now())
                .build());

        // when
        Map<Long, List<LectureOptionInfo>> lectureOptionMap =
            lectureService.getLectureOptionMapByDate(date);

        // then
        assertThat(lectureOptionMap.get(lecture.getLectureId()))
            .hasSize(2)
            .extracting(
                LectureOptionInfo::getLectureOptionId,
                LectureOptionInfo::getLectureId,
                LectureOptionInfo::getMaxApplyCount,
                LectureOptionInfo::getCurrentApplyCount,
                LectureOptionInfo::getApplyStartDate,
                LectureOptionInfo::getApplyEndDate,
                LectureOptionInfo::getLectureStartAt,
                LectureOptionInfo::getLectureEndAt)
            .containsExactlyInAnyOrder(
                tuple(
                    lectureOption1.getLectureOptionId(),
                    lectureOption1.getLectureId(),
                    lectureOption1.getMaxApplyCount(),
                    lectureOption1.getCurrentApplyCount(),
                    lectureOption1.getApplyStartDate(),
                    lectureOption1.getApplyEndDate(),
                    lectureOption1.getLectureStartAt(),
                    lectureOption1.getLectureEndAt()),
                tuple(
                    lectureOption2.getLectureOptionId(),
                    lectureOption2.getLectureId(),
                    lectureOption2.getMaxApplyCount(),
                    lectureOption2.getCurrentApplyCount(),
                    lectureOption2.getApplyStartDate(),
                    lectureOption2.getApplyEndDate(),
                    lectureOption2.getLectureStartAt(),
                    lectureOption2.getLectureEndAt()));
    }

    @DisplayName("lectureOptionId에 해당하는 LectureOption을 조회한다.")
    @Test
    void should_ReturnLectureOptionInfo_When_FindByLectureOptionId() {
        // given
        LectureOption lectureOption = lectureOptionJpaRepository.save(
            LectureOption.builder()
                .lectureId(1L)
                .applyStartDate(LocalDate.now())
                .applyEndDate(LocalDate.now())
                .lectureStartAt(LocalDateTime.now())
                .lectureEndAt(LocalDateTime.now())
                .maxApplyCount(30)
                .currentApplyCount(0)
                .createdAt(LocalDateTime.now())
                .build());

        // when
        LectureOptionInfo result =
            lectureService.getLectureOption(lectureOption.getLectureOptionId());

        // then
        assertThat(result.getLectureOptionId()).isEqualTo(lectureOption.getLectureOptionId());
        assertThat(result.getLectureId()).isEqualTo(lectureOption.getLectureId());
        assertThat(result.getApplyStartDate()).isEqualTo(lectureOption.getApplyStartDate());
        assertThat(result.getApplyEndDate()).isEqualTo(lectureOption.getApplyEndDate());
        assertThat(result.getMaxApplyCount()).isEqualTo(lectureOption.getMaxApplyCount());
        assertThat(result.getCurrentApplyCount()).isEqualTo(lectureOption.getCurrentApplyCount());
        assertThat(result.getLectureStartAt()).isEqualTo(lectureOption.getLectureStartAt());
        assertThat(result.getLectureEndAt()).isEqualTo(lectureOption.getLectureEndAt());
    }

    @DisplayName("lectureOptionId에 해당하는 LectureOption의 applyCount를 증가시킨다.")
    @Test
    void should_IncreaseCurrentApplyCount_When_ByLectureOptionId() {
        // given
        int prevApplyCount = 0;
        LocalDateTime dateTime = LocalDateTime.now();
        LectureOption lectureOption = lectureOptionJpaRepository.save(
            LectureOption.builder()
                .lectureId(1L)
                .applyStartDate(LocalDate.now())
                .applyEndDate(LocalDate.now())
                .lectureStartAt(dateTime)
                .lectureEndAt(dateTime.plusHours(1))
                .maxApplyCount(30)
                .currentApplyCount(prevApplyCount)
                .createdAt(LocalDateTime.now())
                .build());

        // when
        lectureService.increaseCurrentApplyCapacity(lectureOption.getLectureOptionId());

        // then
        LectureOption findLectureOption =
            lectureOptionJpaRepository.findById(lectureOption.getLectureOptionId()).orElse(null);

        assertThat(findLectureOption.getCurrentApplyCount()).isEqualTo(prevApplyCount + 1);
    }

    @DisplayName("CreateApplyHistory객체를 통해 ApplyHistory를 저장한다.")
    @Test
    void should_SaveApplyHistory_When_ByCreateApplyHistory() {
        // given
        CreateApplyHistory createApplyHistory = CreateApplyHistory.builder()
            .memberId(1L)
            .lectureId(1L)
            .lectureOptionId(1L)
            .isSuccess(true)
            .appliedAt(LocalDateTime.now())
            .build();

        // when
        LectureApplyHistoryInfo savedApplyHistory =
            lectureService.saveApplyHistory(createApplyHistory);

        // then
        assertThat(savedApplyHistory.getMemberId()).isEqualTo(createApplyHistory.getMemberId());
        assertThat(savedApplyHistory.getLectureId()).isEqualTo(createApplyHistory.getLectureId());
        assertThat(savedApplyHistory.getLectureOptionId()).isEqualTo(createApplyHistory.getLectureOptionId());
        assertThat(savedApplyHistory.isSuccess()).isEqualTo(createApplyHistory.isSuccess());
    }

    @DisplayName("memberId와 lectureId에 해당하는 LectureHistory가 있다면 true를 반환한다.")
    @Test
    void should_ReturnTrue_When_ExistsByMemberIdAndLectureId() {
        // given
        Long memberId = 1L;
        Long lectureId = 1L;
        LectureApplyHistory lectureApplyHistory1 = lectureApplyHistoryJpaRepository.save(
            LectureApplyHistory.builder()
                .memberId(memberId)
                .lectureId(lectureId)
                .lectureOptionId(1L)
                .success(true)
                .appliedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build());

        // when
        boolean exists = lectureService.existsAppliedLectureHistory(memberId, lectureId);

        // then
        assertThat(exists).isTrue();
    }

    @DisplayName("memberId에 해당하는 LectureApplyHistory목록을 반환한다.")
    @Test
    void should_ReturnLectureApplyHistories_When_ByMemberId() {
        // given
        Long memberId = 1L;

        LectureApplyHistory lectureApplyHistory1 = lectureApplyHistoryJpaRepository.save(
            LectureApplyHistory.builder()
                .memberId(memberId)
                .lectureId(1L)
                .lectureOptionId(1L)
                .success(true)
                .appliedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build());

        LectureApplyHistory lectureApplyHistory2 = lectureApplyHistoryJpaRepository.save(
            LectureApplyHistory.builder()
                .memberId(memberId)
                .lectureId(2L)
                .lectureOptionId(2L)
                .success(true)
                .appliedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build());

        // when
        List<LectureApplyHistoryInfo> applyHistories =
            lectureService.getAppliedLectureHistories(memberId);

        // then
        assertThat(applyHistories).hasSize(2)
            .extracting(
                LectureApplyHistoryInfo::getLectureApplyHistoryId,
                LectureApplyHistoryInfo::getMemberId,
                LectureApplyHistoryInfo::getLectureId,
                LectureApplyHistoryInfo::getLectureOptionId,
                LectureApplyHistoryInfo::isSuccess,
                LectureApplyHistoryInfo::getAppliedAt)
            .containsExactlyInAnyOrder(
                tuple(
                    lectureApplyHistory1.getLectureApplyHistoryId(),
                    lectureApplyHistory1.getMemberId(),
                    lectureApplyHistory1.getLectureId(),
                    lectureApplyHistory1.getLectureOptionId(),
                    lectureApplyHistory1.isSuccess(),
                    lectureApplyHistory1.getAppliedAt()),
                tuple(
                    lectureApplyHistory2.getLectureApplyHistoryId(),
                    lectureApplyHistory2.getMemberId(),
                    lectureApplyHistory2.getLectureId(),
                    lectureApplyHistory2.getLectureOptionId(),
                    lectureApplyHistory2.isSuccess(),
                    lectureApplyHistory2.getAppliedAt()));
    }
}