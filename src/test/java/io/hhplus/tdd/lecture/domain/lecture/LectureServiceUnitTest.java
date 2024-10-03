package io.hhplus.tdd.lecture.domain.lecture;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import io.hhplus.tdd.lecture.domain.lecture.exception.LectureErrorCode;
import io.hhplus.tdd.lecture.domain.lecture.exception.LectureException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LectureServiceUnitTest {

    @Mock
    private LectureRepository lectureRepository;
    
    @InjectMocks
    private LectureService lectureService;
    
    @DisplayName("lectureId에 해당하는 lecture가 존재하지 않으면 LectureException이 발생한다.")
    @Test
    void should_ThrowLectureException_When_LectureNotExist() {
        // given
        Long lectureId = 0L;
        when(lectureRepository.existsLecture(lectureId))
            .thenReturn(false);
        
        // when, then
        assertThatThrownBy(() -> lectureService.checkLectureExists(lectureId))
            .isInstanceOf(LectureException.class)
            .hasMessage(LectureErrorCode.NOT_FOUND_LECTURE.getMessage());
    }
}