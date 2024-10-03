package io.hhplus.tdd.lecture.application.lecture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.hhplus.tdd.lecture.domain.lecture.LectureMapper;
import io.hhplus.tdd.lecture.domain.lecture.LectureService;
import io.hhplus.tdd.lecture.domain.lecture.exception.LectureErrorCode;
import io.hhplus.tdd.lecture.domain.lecture.exception.LectureException;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureCommand.CreateApplyHistory;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureOptionInfo;
import io.hhplus.tdd.lecture.domain.member.MemberService;
import io.hhplus.tdd.lecture.domain.member.model.MemberInfo;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LectureFacadeUnitTest {

    @Mock
    private LectureService lectureService;

    @Mock
    private MemberService memberService;

    @Mock
    private LectureMapper lectureMapper;

    @InjectMocks
    private LectureFacade lectureFacade;

    @DisplayName("특강 신청 test")
    @Nested
    class ApplyLectureTest {
        @DisplayName("이미 신청한 강의에 대해 신청하면 LectureException이 발생한다.")
        @Test
        void should_ThrowLectureException_When_AlreadyAppliedLecture () {
            // given
            Long memberId = 1L;
            Long lectureOptionId = 1L;
            Long lectureId = 1L;
            this.commonMocking(memberId, lectureOptionId, lectureId);

            when(lectureService.existsAppliedLectureHistory(memberId, lectureId))
                .thenReturn(true);

            // when, then
            assertThatThrownBy(() -> lectureFacade.applyLecture(memberId, lectureOptionId))
                .isInstanceOf(LectureException.class)
                .hasMessage(LectureErrorCode.ALREADY_APPLY_LECTURE.getMessage());
        }

        @DisplayName("정원이 다 찬 강의에 대해 신청하면 LectureException이 발생한다.")
        @Test
        void should_ThrowLectureException_When_MaxCapacity() {
            // given
            Long memberId = 1L;
            Long lectureOptionId = 1L;
            Long lectureId = 1L;
            MemberInfo memberInfo = MemberInfo.builder()
                .memberId(memberId)
                .name("name")
                .build();

            LectureInfo lectureInfo = LectureInfo.builder()
                .lectureId(lectureId)
                .title("title")
                .description("desc")
                .lecturerName("name")
                .build();

            LectureOptionInfo lectureOptionInfo = LectureOptionInfo.builder()
                .lectureOptionId(lectureOptionId)
                .lectureId(lectureId)
                .maxApplyCount(30)
                .currentApplyCount(30)
                .applyEndDate(LocalDate.now())
                .applyEndDate(LocalDate.now())
                .build();

            when(memberService.getMember(memberId))
                .thenReturn(memberInfo);
            when(lectureService.getLectureOption(lectureOptionId))
                .thenReturn(lectureOptionInfo);
            when(lectureService.getLecture(lectureId))
                .thenReturn(lectureInfo);

            when(lectureService.existsAppliedLectureHistory(memberId, lectureId))
                .thenReturn(false);

            // when, then
            assertThatThrownBy(() -> lectureFacade.applyLecture(memberId, lectureOptionId))
                .isInstanceOf(LectureException.class)
                .hasMessage(LectureErrorCode.EXCEED_MAX_CAPACITY.getMessage());
        }

        @DisplayName("수강신청에 성공하면 capcity가 1 증가한다,")
        @Test
        void should_IncreaseCapacity_When_ApplySuccess() {
            // given
            Long memberId = 1L;
            Long lectureOptionId = 1L;
            Long lectureId = 1L;
            this.commonMocking(memberId, lectureOptionId, lectureId);
            when(lectureService.existsAppliedLectureHistory(memberId, lectureId))
                .thenReturn(false);

            // when
            lectureFacade.applyLecture(memberId, lectureOptionId);

            // then
            ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
            verify(lectureService).increaseCurrentApplyCapacity(captor.capture());
            Long value = captor.getValue();
            assertThat(value).isEqualTo(lectureOptionId);
        }
        
        @DisplayName("수강신청에 성공하면 History가 저장된다.")
        @Test
        void should_SaveHistory_When_ApplySuccess() {
            // given
            Long memberId = 1L;
            Long lectureOptionId = 1L;
            Long lectureId = 1L;
            this.commonMocking(memberId, lectureOptionId, lectureId);
            when(lectureService.existsAppliedLectureHistory(memberId, lectureId))
                .thenReturn(false);

            // when
            lectureFacade.applyLecture(memberId, lectureOptionId);
        
            // then
            ArgumentCaptor<CreateApplyHistory> captor = ArgumentCaptor.forClass(CreateApplyHistory.class);
            verify(lectureService).saveApplyHistory(captor.capture());
            CreateApplyHistory value = captor.getValue();

            assertThat(value.getMemberId()).isEqualTo(memberId);
            assertThat(value.getLectureId()).isEqualTo(lectureId);
            assertThat(value.getLectureOptionId()).isEqualTo(lectureOptionId);
            assertThat(value.isSuccess()).isEqualTo(true);
        }

        private void commonMocking(Long memberId, Long lectureOptionId, Long lectureId) {
            MemberInfo memberInfo = MemberInfo.builder()
                .memberId(memberId)
                .name("name")
                .build();

            LectureInfo lectureInfo = LectureInfo.builder()
                .lectureId(lectureId)
                .title("title")
                .description("desc")
                .lecturerName("name")
                .build();

            LectureOptionInfo lectureOptionInfo = LectureOptionInfo.builder()
                .lectureOptionId(lectureOptionId)
                .lectureId(lectureId)
                .maxApplyCount(30)
                .currentApplyCount(0)
                .applyEndDate(LocalDate.now())
                .applyEndDate(LocalDate.now())
                .build();

            when(memberService.getMember(memberId))
                .thenReturn(memberInfo);
            when(lectureService.getLectureOption(lectureOptionId))
                .thenReturn(lectureOptionInfo);
            when(lectureService.getLecture(lectureId))
                .thenReturn(lectureInfo);
        }
    }
}