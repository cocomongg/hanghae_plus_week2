package io.hhplus.tdd.lecture.domain.lecture.exception;

import io.hhplus.tdd.lecture.domain.common.exception.DomainErrorCode;
import io.hhplus.tdd.lecture.domain.common.exception.ErrorInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LectureErrorCode implements DomainErrorCode {
    NOT_POSSIBLE_APPLY(400, "400_1", "Can't apply"),
    NOT_FOUND_LECTURE_OPTION(404, "404_1", "LectureOption not found"),
    NOT_FOUND_LECTURE_CAPACITY(404, "404_2", "LectureCapacity not found"),
    NOT_FOUND_LECTURE(404, "404_3", "Lecture not found");

    private final int status;
    private final String code;
    private final String message;

    @Override
    public ErrorInfo getErrorInfo() {
        return new ErrorInfo(this.status, this.code, this.message);
    }
}
