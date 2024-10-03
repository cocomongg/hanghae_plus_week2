package io.hhplus.tdd.lecture.infrastructure.db.lecture.repository.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.hhplus.tdd.lecture.domain.lecture.exception.LectureErrorCode;
import io.hhplus.tdd.lecture.domain.lecture.exception.LectureException;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureOptionInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureStatus;
import io.hhplus.tdd.lecture.domain.lecture.model.LecturerInfo;
import io.hhplus.tdd.lecture.infrastructure.db.lecture.entity.Lecture;
import io.hhplus.tdd.lecture.infrastructure.db.lecture.entity.LectureOption;
import io.hhplus.tdd.lecture.infrastructure.db.lecture.entity.Lecturer;
import io.hhplus.tdd.lecture.infrastructure.db.lecture.repository.LectureJpaRepository;
import io.hhplus.tdd.lecture.infrastructure.db.lecture.repository.LectureOptionJpaRepository;
import io.hhplus.tdd.lecture.infrastructure.db.lecture.repository.LecturerJpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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
    private LecturerJpaRepository lecturerJpaRepository;

    @Mock
    private LectureOptionJpaRepository lectureOptionJpaRepository;

    @InjectMocks
    private LectureRepositoryImpl lectureRepositoryImpl;

    @DisplayName("Lecture List 조회 테스트")
    @Nested
    class GetLectureInfosTest {
        @DisplayName("조회된 lecture 목록을 LectureInfo 목록으로 변환하여 반환한다.")
        @Test
        void should_ReturnLectureInfoList_When_Found() {
            // given
            Lecture lecture1 = Lecture.builder()
                .lectureId(1L)
                .title("title")
                .description("description")
                .lecturerId(1L)
                .createdAt(LocalDateTime.now())
                .build();

            Lecture lecture2 = Lecture.builder()
                .lectureId(2L)
                .title("title2")
                .description("description2")
                .lecturerId(2L)
                .createdAt(LocalDateTime.now())
                .build();

            when(lectureJpaRepository.findAll())
                .thenReturn(List.of(lecture1, lecture2));

            // when
            List<LectureInfo> lectureInfos = lectureRepositoryImpl.getLectures();

            // then
            assertThat(lectureInfos).hasSize(2)
                .extracting(LectureInfo::getLectureId, LectureInfo::getTitle,
                    LectureInfo::getDescription, LectureInfo::getLecturerId)
                .containsExactlyInAnyOrder(
                    tuple(lecture1.getLectureId(), lecture1.getTitle(), lecture1.getDescription(),
                        lecture1.getLecturerId()),
                    tuple(lecture2.getLectureId(), lecture2.getTitle(), lecture2.getDescription(),
                        lecture2.getLecturerId())
                );


        }
    }

    @DisplayName("Lecturer List 조회 테스트")
    @Nested
    class GetLecturerInfosTest {
        @DisplayName("조회된 lecturer목록을 LectureInfo목록으로 변환하여 반환한다.")
        @Test
        void should_ReturnLecturerInfoMap() {
            // given
            List<Long> lecturerIds = List.of(1L, 2L);
            Lecturer lecturer1 = Lecturer.builder()
                .lecturerId(1L)
                .name("강사1")
                .createdAt(LocalDateTime.now())
                .build();

            Lecturer lecturer2 = Lecturer.builder()
                .lecturerId(2L)
                .name("강사2")
                .createdAt(LocalDateTime.now())
                .build();

            when(lecturerJpaRepository.findAllById(lecturerIds))
                .thenReturn(List.of(lecturer1, lecturer2));

            // when
            List<LecturerInfo> lecturers = lectureRepositoryImpl.getLecturers(lecturerIds);

            // then
            assertThat(lecturers)
                .hasSize(2)
                .extracting(LecturerInfo::getLecturerId, LecturerInfo::getName)
                .containsExactlyInAnyOrder(
                    tuple(lecturer1.getLecturerId(), lecturer1.getName()),
                    tuple(lecturer2.getLecturerId(), lecturer2.getName())
                );
        }
    }

    @DisplayName("LectureOption List 조회 테스트")
    @Nested
    class GetLectureOptionsTest {
        @DisplayName("조회된 lectureOption 목록을 LectureOptionInfo 목록으로 변환하여 반환한다.")
        @Test
        void should_ReturnLectureInfoList_When_Found() {
            // given
            Long lectureId = 1L;
            LocalDateTime now = LocalDateTime.now();
            LectureOption lectureOption1 = LectureOption.builder()
                .lectureOptionId(1L)
                .lectureId(lectureId)
                .status(LectureStatus.START)
                .lectureStartAt(now)
                .lectureEndAt(now)
                .maxApplyCount(30)
                .createdAt(now)
                .build();

            LectureOption lectureOption2 = LectureOption.builder()
                .lectureOptionId(2L)
                .lectureId(lectureId)
                .status(LectureStatus.END)
                .lectureStartAt(now)
                .lectureEndAt(now)
                .maxApplyCount(30)
                .createdAt(now)
                .build();

            when(lectureOptionJpaRepository.findAllByLectureId(lectureId))
                .thenReturn(List.of(lectureOption1, lectureOption2));

            // when
            List<LectureOptionInfo> lectureOptionInfos = lectureRepositoryImpl.getLectureOptions(lectureId);

            // then
            assertThat(lectureOptionInfos).hasSize(2)
                .extracting(LectureOptionInfo::getLectureId, LectureOptionInfo::getLectureId,
                    LectureOptionInfo::getStatus, LectureOptionInfo::getLectureStartAt,
                    LectureOptionInfo::getLectureEndAt, LectureOptionInfo::getMaxApplyCount,
                    LectureOptionInfo::getCreatedAt)
                .containsExactlyInAnyOrder(
                    tuple(lectureOption1.getLectureId(), lectureOption1.getLectureId(),
                        lectureOption1.getStatus(), lectureOption1.getLectureStartAt(),
                        lectureOption1.getLectureEndAt(), lectureOption1.getMaxApplyCount(),
                        lectureOption1.getCreatedAt()),
                    tuple(lectureOption2.getLectureId(), lectureOption2.getLectureId(),
                        lectureOption2.getStatus(), lectureOption2.getLectureStartAt(),
                        lectureOption2.getLectureEndAt(), lectureOption2.getMaxApplyCount(),
                        lectureOption2.getCreatedAt())
                );
        }
    }

    @DisplayName("LectureOption 조회 테스트")
    @Nested
    class GetLectureOptionTest {
        @DisplayName("lectureOptionId에 해당하는 LectureOption이 없다면 LectureException이 발생한다.")
        @Test
        void should_ThrowLectureException_When_LectureOptionNotFound() {
            // given
            Long lectureOptionId = 0L;
            when(lectureOptionJpaRepository.findById(lectureOptionId))
                .thenReturn(Optional.empty());

            // when, then
            assertThatThrownBy(() -> lectureRepositoryImpl.getLectureOption(lectureOptionId))
                .isInstanceOf(LectureException.class)
                .hasMessage(LectureErrorCode.NOT_FOUND_LECTURE_OPTION.getMessage());
        }

        @DisplayName("lectureOptionId에 해당하는 LectureOption이 있다면 LectureOptionInfo로 변환하여 반환한다.")
        @Test
        void should_ReturnLectureOptionInfo_When_LectureOptionFound() {
            // given
            Long lectureOptionId = 1L;
            LocalDateTime now = LocalDateTime.now();

            LectureOption lectureOption = LectureOption.builder()
                .lectureOptionId(lectureOptionId)
                .lectureId(1L)
                .status(LectureStatus.APPLYING)
                .lectureStartAt(now)
                .lectureEndAt(now)
                .maxApplyCount(30)
                .currentApplyCount(10)
                .createdAt(now)
                .build();

            when(lectureOptionJpaRepository.findById(lectureOptionId))
                .thenReturn(Optional.of(lectureOption));

            // when
            LectureOptionInfo lectureOptionInfo =
                lectureRepositoryImpl.getLectureOption(lectureOptionId);

            // then
            assertThat(lectureOption.getLectureOptionId()).isEqualTo(lectureOptionInfo.getLectureOptionId());
            assertThat(lectureOption.getLectureId()).isEqualTo(lectureOptionInfo.getLectureId());
            assertThat(lectureOption.getStatus()).isEqualTo(lectureOptionInfo.getStatus());
            assertThat(lectureOption.getLectureStartAt()).isEqualTo(lectureOptionInfo.getLectureStartAt());
            assertThat(lectureOption.getLectureEndAt()).isEqualTo(lectureOptionInfo.getLectureEndAt());
            assertThat(lectureOption.getMaxApplyCount()).isEqualTo(lectureOptionInfo.getMaxApplyCount());
            assertThat(lectureOption.getCurrentApplyCount()).isEqualTo(lectureOptionInfo.getCurrentApplyCount());
            assertThat(lectureOption.getCreatedAt()).isEqualTo(lectureOptionInfo.getCreatedAt());
        }
    }

    @DisplayName("LectureOption currentApplyCount 업데이트 테스트")
    @Nested
    class IncreaseCurrentApplyCapacityTest {
        @DisplayName("lectureOptionId에 해당하는 LectureOption이 없다면 LectureException이 발생한다.")
        @Test
        void should_ThrowLectureException_When_LectureOptionNotFound() {
            // given
            Long lectureOptionId = 0L;
            when(lectureOptionJpaRepository.findById(lectureOptionId))
                .thenReturn(Optional.empty());

            // when, then
            assertThatThrownBy(() -> lectureRepositoryImpl.getLectureOption(lectureOptionId))
                .isInstanceOf(LectureException.class)
                .hasMessage(LectureErrorCode.NOT_FOUND_LECTURE_OPTION.getMessage());
        }

        @DisplayName("존재하는 LectureOption의 currentApplyCount를 1 증가시킨다.")
        @Test
        void should_IncreaseCurrentApplyCount_When_LectureOptionFound() {
            // given
            Long lectureOptionId = 1L;
            int prevApplyCount = 10;
            LocalDateTime now = LocalDateTime.now();

            LectureOption lectureOption = LectureOption.builder()
                .lectureOptionId(lectureOptionId)
                .lectureId(1L)
                .status(LectureStatus.APPLYING)
                .lectureStartAt(now)
                .lectureEndAt(now)
                .maxApplyCount(30)
                .currentApplyCount(10)
                .createdAt(now)
                .build();

            when(lectureOptionJpaRepository.findById(lectureOptionId))
                .thenReturn(Optional.of(lectureOption));

            // when
            LectureOptionInfo lectureOptionInfo = lectureRepositoryImpl.increaseCurrentApplyCapacity(
                lectureOptionId);

            // then
            assertThat(lectureOptionInfo.getLectureOptionId()).isEqualTo(lectureOption.getLectureOptionId());
            assertThat(lectureOptionInfo.getCurrentApplyCount()).isEqualTo(prevApplyCount + 1);
        }
    }
}