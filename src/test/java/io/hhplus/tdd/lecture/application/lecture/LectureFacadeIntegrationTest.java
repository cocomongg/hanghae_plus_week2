package io.hhplus.tdd.lecture.application.lecture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

import io.hhplus.tdd.lecture.domain.lecture.exception.LectureErrorCode;
import io.hhplus.tdd.lecture.domain.lecture.exception.LectureException;
import io.hhplus.tdd.lecture.domain.lecture.model.ApplyHistoryWithLecture;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureOptionInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureWithOption;
import io.hhplus.tdd.lecture.domain.member.exception.MemberErrorCode;
import io.hhplus.tdd.lecture.domain.member.exception.MemberException;
import io.hhplus.tdd.lecture.infrastructure.db.entity.lecture.Lecture;
import io.hhplus.tdd.lecture.infrastructure.db.entity.lecture.LectureApplyHistory;
import io.hhplus.tdd.lecture.infrastructure.db.entity.lecture.LectureOption;
import io.hhplus.tdd.lecture.infrastructure.db.entity.member.Member;
import io.hhplus.tdd.lecture.infrastructure.db.repository.lecture.LectureApplyHistoryJpaRepository;
import io.hhplus.tdd.lecture.infrastructure.db.repository.lecture.LectureJpaRepository;
import io.hhplus.tdd.lecture.infrastructure.db.repository.lecture.LectureOptionJpaRepository;
import io.hhplus.tdd.lecture.infrastructure.db.repository.member.MemberJpaRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LectureFacadeIntegrationTest {

    @Autowired
    private LectureJpaRepository lectureJpaRepository;

    @Autowired
    private LectureOptionJpaRepository lectureOptionJpaRepository;

    @Autowired
    private LectureApplyHistoryJpaRepository lectureApplyHistoryJpaRepository;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Autowired
    private LectureFacade lectureFacade;

    @BeforeEach
    public void setup() {
        lectureJpaRepository.deleteAllInBatch();
        lectureApplyHistoryJpaRepository.deleteAllInBatch();
        lectureOptionJpaRepository.deleteAllInBatch();
        memberJpaRepository.deleteAllInBatch();
    }

    @DisplayName("신청가능한 특강 목록 조회 테스트")
    @Nested
    class GetApplicableLecturesTest {
        @DisplayName("date에 신청 가능한 특강 목록을 반환한다.")
        @Test
        void should_ReturnLectureWithOptionList_When_FindByDate() {
            // given
            LocalDate date = LocalDate.now();

            Lecture lecture1 = lectureJpaRepository.save(Lecture.builder()
                .title("title1")
                .description("desc1")
                .lecturerName("name1")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());

            LectureOption lectureOption1 = lectureOptionJpaRepository.save(
                LectureOption.builder()
                    .lectureId(lecture1.getLectureId())
                    .applyStartDate(date)
                    .applyEndDate(date.plusDays(1))
                    .maxApplyCount(30)
                    .currentApplyCount(0)
                    .createdAt(LocalDateTime.now())
                    .build()
            );

            LectureOption lectureOption2 = lectureOptionJpaRepository.save(
                LectureOption.builder()
                    .lectureOptionId(1L)
                    .lectureId(lecture1.getLectureId())
                    .applyStartDate(date)
                    .applyEndDate(date.plusDays(1))
                    .maxApplyCount(30)
                    .currentApplyCount(0)
                    .createdAt(LocalDateTime.now())
                    .build()
            );

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

    @DisplayName("특강 신청 테스트")
    @Nested
    class ApplyLectureTest {
        @DisplayName("존재하지 않는 member이면 MemberException이 발생한다.")
        @Test
        void should_ThrowMemberException_When_MemberNotFound() {
            // given
            Long memberId = 0L;

            // when, then
            assertThatThrownBy(() -> lectureFacade.applyLecture(memberId, 1L))
                .isInstanceOf(MemberException.class)
                .hasMessage(MemberErrorCode.NOT_FOUND_MEMBER.getMessage());
        }

        @DisplayName("존재하지 않는 lectureOption이면 LectureException이 발생한다.")
        @Test
        void should_ThrowLectureException_When_LectureOptionNotFound() {
            // given
            Long lectureOptionId = 0L;

            Member member = memberJpaRepository.save(
                Member.builder()
                .name("name")
                .createdAt(LocalDateTime.now())
                .build()
            );

            // when, then
            assertThatThrownBy(() -> lectureFacade.applyLecture(member.getId(), lectureOptionId))
                .isInstanceOf(LectureException.class)
                .hasMessage(LectureErrorCode.NOT_FOUND_LECTURE_OPTION.getMessage());
        }

        @DisplayName("존재하지 않는 lecture이면 LectureException이 발생한다.")
        @Test
        void should_ThrowLectureException_When_LectureNotFound() {
            // given
            Member member = memberJpaRepository.save(
                Member.builder()
                    .name("name")
                    .createdAt(LocalDateTime.now())
                    .build());

            LectureOption lectureOption = lectureOptionJpaRepository.save(
                LectureOption.builder()
                    .lectureId(0L)
                    .applyStartDate(LocalDate.now())
                    .applyEndDate(LocalDate.now().plusDays(1))
                    .maxApplyCount(30)
                    .currentApplyCount(0)
                    .createdAt(LocalDateTime.now())
                    .build()
            );

            // when, then
            assertThatThrownBy(() -> lectureFacade.applyLecture(member.getId(), lectureOption.getLectureOptionId()))
                .isInstanceOf(LectureException.class)
                .hasMessage(LectureErrorCode.NOT_FOUND_LECTURE.getMessage());
        }

        @DisplayName("이미 신청한 강의이면 LectureException이 발생한다.")
        @Test
        void should_ThrowLectureException_When_AlreadyApply() {
            // given
            Member member = memberJpaRepository.save(
                Member.builder()
                    .name("name")
                    .createdAt(LocalDateTime.now())
                    .build());

            Lecture lecture = lectureJpaRepository.save(
                Lecture.builder()
                    .title("title1")
                    .description("desc1")
                    .lecturerName("name1")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build()
            );

            LectureOption lectureOption = lectureOptionJpaRepository.save(
                LectureOption.builder()
                    .lectureId(lecture.getLectureId())
                    .applyStartDate(LocalDate.now())
                    .applyEndDate(LocalDate.now().plusDays(1))
                    .maxApplyCount(30)
                    .currentApplyCount(0)
                    .createdAt(LocalDateTime.now())
                    .build()
            );

            LectureApplyHistory lectureApplyHistory = lectureApplyHistoryJpaRepository.save(
                LectureApplyHistory.builder()
                    .memberId(member.getId())
                    .lectureId(lecture.getLectureId())
                    .lectureOptionId(lectureOption.getLectureOptionId())
                    .success(true)
                    .appliedAt(LocalDateTime.now())
                    .createdAt(LocalDateTime.now())
                    .build()
            );

            // when, then
            assertThatThrownBy(() -> lectureFacade.applyLecture(member.getId(), lectureOption.getLectureOptionId()))
                .isInstanceOf(LectureException.class)
                .hasMessage(LectureErrorCode.ALREADY_APPLY_LECTURE.getMessage());
        }

        @DisplayName("신청한 강의가 정원이 이미 다 찼다면 LectureException이 발생한다.")
        @Test
        void should_ThrowLectureException_When_ExceedMaxCapacity() {
            // given
            Member member = memberJpaRepository.save(
                Member.builder()
                    .name("name")
                    .createdAt(LocalDateTime.now())
                    .build());

            Lecture lecture = lectureJpaRepository.save(
                Lecture.builder()
                    .title("title1")
                    .description("desc1")
                    .lecturerName("name1")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build()
            );

            LectureOption lectureOption = lectureOptionJpaRepository.save(
                LectureOption.builder()
                    .lectureId(lecture.getLectureId())
                    .applyStartDate(LocalDate.now())
                    .applyEndDate(LocalDate.now().plusDays(1))
                    .maxApplyCount(30)
                    .currentApplyCount(30)
                    .createdAt(LocalDateTime.now())
                    .build()
            );

            // when, then
            assertThatThrownBy(() -> lectureFacade.applyLecture(member.getId(), lectureOption.getLectureOptionId()))
                .isInstanceOf(LectureException.class)
                .hasMessage(LectureErrorCode.EXCEED_MAX_CAPACITY.getMessage());
        }

        @DisplayName("특강을 성공적으로 신청하면 Capacity가 1 늘어난다")
        @Test
        void should_IncreaseCapacity_When_ApplyLecture() {
            // given
            int prevApplyCount = 10;
            Member member = memberJpaRepository.save(
                Member.builder()
                    .name("name")
                    .createdAt(LocalDateTime.now())
                    .build());

            Lecture lecture = lectureJpaRepository.save(
                Lecture.builder()
                    .title("title1")
                    .description("desc1")
                    .lecturerName("name1")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build());

            LectureOption lectureOption = lectureOptionJpaRepository.save(
                LectureOption.builder()
                    .lectureId(lecture.getLectureId())
                    .applyStartDate(LocalDate.now())
                    .applyEndDate(LocalDate.now().plusDays(1))
                    .maxApplyCount(30)
                    .currentApplyCount(prevApplyCount)
                    .createdAt(LocalDateTime.now())
                    .build());

            // when
            lectureFacade.applyLecture(member.getId(), lectureOption.getLectureOptionId());

            // then
            Optional<LectureOption> lectureOptionOptional =
                lectureOptionJpaRepository.findById(lectureOption.getLectureOptionId());
            assertThat(lectureOptionOptional).isPresent();

            LectureOption getLectureOption = lectureOptionOptional.get();
            assertThat(getLectureOption.getCurrentApplyCount()).isEqualTo(prevApplyCount + 1);
        }
    }
    
    @DisplayName("특강 신청 내역 조회 테스트")
    @Nested
    class getAppliedLectureHistories {
        @DisplayName("memberId에 해당하는 member가 없으면 MemberException이 발생한다.")
        @Test
        void should_ThrowMemberException_When_NotFoundMember() {
            // given
            Long memberId = 0L;
            
            // when, then
            assertThatThrownBy(() -> lectureFacade.getAppliedLectureHistories(memberId))
                .isInstanceOf(MemberException.class)
                .hasMessage(MemberErrorCode.NOT_FOUND_MEMBER.getMessage());
        }
        
        @DisplayName("memberId에 해당하는 특강 신청 내역이 있으면 신청한 특강에 대한 목록을 반환한다.")
        @Test
        void should_ReturnHistoryList_When_HistoryExits() {
            // given
            LocalDateTime now = LocalDateTime.now();
            Member member = memberJpaRepository.save(
                Member.builder()
                    .name("name")
                    .createdAt(LocalDateTime.now())
                    .build()
            );

            Lecture lecture1 = lectureJpaRepository.save(Lecture.builder()
                .title("title1")
                .description("desc1")
                .lecturerName("name1")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());

            Lecture lecture2 = lectureJpaRepository.save(Lecture.builder()
                .title("title2")
                .description("desc2")
                .lecturerName("name2")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());

            LectureApplyHistory lectureApplyHistory1 = lectureApplyHistoryJpaRepository.save(
                LectureApplyHistory.builder()
                    .memberId(member.getId())
                    .lectureId(lecture1.getLectureId())
                    .lectureOptionId(1L)
                    .success(true)
                    .appliedAt(now)
                    .createdAt(now)
                    .build());

            LectureApplyHistory lectureApplyHistory2 = lectureApplyHistoryJpaRepository.save(
                LectureApplyHistory.builder()
                    .memberId(member.getId())
                    .lectureId(lecture2.getLectureId())
                    .lectureOptionId(1L)
                    .success(true)
                    .appliedAt(now)
                    .createdAt(now)
                    .build());

            // when
            List<ApplyHistoryWithLecture> result = lectureFacade.getAppliedLectureHistories(
                member.getId());

            // then
            assertThat(result).hasSize(2)
                .extracting(
                    ApplyHistoryWithLecture::getLectureApplyHistoryId,
                    ApplyHistoryWithLecture::getLectureId,
                    ApplyHistoryWithLecture::getLectureOptionId,
                    ApplyHistoryWithLecture::getLectureTitle,
                    ApplyHistoryWithLecture::getLectureDescription,
                    ApplyHistoryWithLecture::getLecturerName,
                    ApplyHistoryWithLecture::getAppliedAt)
                .containsExactlyInAnyOrder(
                    tuple(
                        lectureApplyHistory1.getLectureApplyHistoryId(),
                        lecture1.getLectureId(),
                        lectureApplyHistory1.getLectureOptionId(),
                        lecture1.getTitle(),
                        lecture1.getDescription(),
                        lecture1.getLecturerName(),
                        lectureApplyHistory1.getAppliedAt()
                    ),
                    tuple(
                        lectureApplyHistory2.getLectureApplyHistoryId(),
                        lecture2.getLectureId(),
                        lectureApplyHistory2.getLectureOptionId(),
                        lecture2.getTitle(),
                        lecture2.getDescription(),
                        lecture2.getLecturerName(),
                        lectureApplyHistory2.getAppliedAt()
                    )
                );
        }
    }
}