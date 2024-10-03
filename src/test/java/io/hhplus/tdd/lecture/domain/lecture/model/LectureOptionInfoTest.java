package io.hhplus.tdd.lecture.domain.lecture.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class LectureOptionInfoTest {

    @DisplayName("isApplicable 테스트")
    @Nested
    class IsApplicableTest {
        @DisplayName("강의 상태가 APPLYING이 아니라면 false를 반환한다.")
        @Test
        void should_ReturnFalse_When_StatusIsNotApplying() {
            // given
            LectureOptionInfo lectureOptionInfo = LectureOptionInfo.builder()
                .status(LectureStatus.START)
                .build();

            // when
            boolean applicable = lectureOptionInfo.isApplicable(0);

            // then
            assertThat(applicable).isFalse();
        }

        @DisplayName("입력받은 현재 강의 수강생이 최대 수강생보다 크거나 같으면 false를 반환한다.")
        @Test
        void should_ReturnFalse_When_CurrentApplyCountExceedMaxCount() {
            // given
            LectureOptionInfo lectureOptionInfo = LectureOptionInfo.builder()
                .maxApplyCount(30)
                .build();

            // when
            boolean applicable = lectureOptionInfo.isApplicable(30);

            // then
            assertThat(applicable).isFalse();
        }

        @DisplayName("현재 수강상태가 APPLYING이고 현재 수강생이 최대보다 적다면 true반환")
        @Test
        void should_ReturnTrue_When_SatisfiedStatusAndApplyCountCondition() {
            // given
            LectureOptionInfo lectureOptionInfo = LectureOptionInfo.builder()
                .maxApplyCount(30)
                .status(LectureStatus.APPLYING)
                .build();

            // when
            boolean applicable = lectureOptionInfo.isApplicable(10);

            // then
            assertThat(applicable).isTrue();
        }
    }
}