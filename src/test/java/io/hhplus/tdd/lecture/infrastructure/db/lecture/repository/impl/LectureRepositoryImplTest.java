package io.hhplus.tdd.lecture.infrastructure.db.lecture.repository.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.when;

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
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    class GetLectureInfoTest {
        @DisplayName("lecutre 목록 조회 시 목록이 비었으면 EntityNotFoundException아 발생한다.")
        @Test
        void should_ThrowEntityNotFoundException_When_NotFound () {
            // given
            when(lectureJpaRepository.findAll())
                .thenReturn(new ArrayList<>());

            // when, then
            assertThatThrownBy(() -> lectureRepositoryImpl.getLectures())
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Lecturers not found");
        }

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

    @DisplayName("Lecturer Map 조회 테스트")
    @Nested
    class GetLecturerMapTest {
        @DisplayName("lecturer 목록 조회 시 목록이 비었으면 EntityNotFoundException이 발생한다.")
        @Test
        void should_ThrowEntityNotFoundException_When_NotFound () {
            // given
            List<Long> lecturerIds = List.of(1L, 2L);
            when(lecturerJpaRepository.findAllById(lecturerIds))
                .thenReturn(new ArrayList<>());

            // when, then
            assertThatThrownBy(() -> lectureRepositoryImpl.getLecturerMap(lecturerIds))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Lecturers not found");
        }

        @DisplayName("조회된 lecturer목록을 lecturer id를 키로 하고, LecturerInfo를 value로 변환하여 반환한다.")
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
            Map<Long, LecturerInfo> lecturerMap = lectureRepositoryImpl.getLecturerMap(lecturerIds);

            // then
            LecturerInfo lecturerInfo1 = lecturerMap.get(1L);
            assertThat(lecturerInfo1.getLecturerId()).isEqualTo(lecturer1.getLecturerId());
            assertThat(lecturerInfo1.getName()).isEqualTo(lecturer1.getName());

            LecturerInfo lecturerInfo2 = lecturerMap.get(2L);
            assertThat(lecturerInfo2.getLecturerId()).isEqualTo(lecturer2.getLecturerId());
            assertThat(lecturerInfo2.getName()).isEqualTo(lecturer2.getName());
        }
    }
    
    @DisplayName("LectureOption Map 조회 테스트")
    @Nested
    class GetLectureOptionMapTest {
        @DisplayName("lectureOption 목록 조회 시 목록이 비었으면 EntityNotFoundException이 발생한다.")
        @Test
        void should_ThrowEntityNotFoundException_When_NotFound() {
            // given
            List<Long> lectureIds = List.of(1L, 2L);
            when(lectureOptionJpaRepository.findAllByLectureIdIn(lectureIds))
                .thenReturn(new ArrayList<>());

            // when, then
            assertThatThrownBy(() -> lectureRepositoryImpl.getLectureOptionMap(lectureIds))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("LectureOptions not found");
        }

        @DisplayName("조회된 lectureOption목록을 lecture id를 키로 하고, LectureOptionInfo목록을 value로 변환하여 반환한다.")
        @Test
        void should_ReturnLectureOptionMap_When_Found() {
            // given
            List<Long> lectureIds = List.of(1L);
            LectureOption lectureOption1 = LectureOption.builder()
                .lectureOptionId(1L)
                .lectureId(1L)
                .status(LectureStatus.START)
                .createdAt(LocalDateTime.now())
                .build();

            LectureOption lectureOption2 = LectureOption.builder()
                .lectureOptionId(2L)
                .lectureId(1L)
                .status(LectureStatus.START)
                .createdAt(LocalDateTime.now())
                .build();

            when(lectureOptionJpaRepository.findAllByLectureIdIn(lectureIds))
                .thenReturn(List.of(lectureOption1, lectureOption2));

            // when
            Map<Long, List<LectureOptionInfo>> lectureOptionMap =
                lectureRepositoryImpl.getLectureOptionMap(lectureIds);

            // then
            List<LectureOptionInfo> lectureOptionInfos = lectureOptionMap.get(1L);
            assertThat(lectureOptionInfos)
                .hasSize(2)
                .extracting(LectureOptionInfo::getLectureOptionId, LectureOptionInfo::getLectureId,
                    LectureOptionInfo::getStatus, LectureOptionInfo::getCreatedAt)
                .containsExactlyInAnyOrder(
                    tuple(lectureOption1.getLectureOptionId(), lectureOption1.getLectureId(),
                        lectureOption1.getStatus(), lectureOption1.getCreatedAt()),
                    tuple(lectureOption2.getLectureOptionId(), lectureOption2.getLectureId(),
                        lectureOption2.getStatus(), lectureOption2.getCreatedAt())
                );
        }
    }

}