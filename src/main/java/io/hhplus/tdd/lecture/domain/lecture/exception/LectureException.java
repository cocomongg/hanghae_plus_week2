package io.hhplus.tdd.lecture.domain.lecture.exception;

import io.hhplus.tdd.lecture.domain.common.exception.DomainErrorCode;
import io.hhplus.tdd.lecture.domain.common.exception.DomainException;

public class LectureException extends DomainException {

    public static final LectureException NOT_POSSIBLE_APPLY =
        new LectureException(LectureErrorCode.NOT_POSSIBLE_APPLY);

    public static final LectureException NOT_FOUND_LECTURE_OPTION =
        new LectureException(LectureErrorCode.NOT_FOUND_LECTURE_OPTION);

    public static final LectureException NOT_FOUND_LECTURE =
        new LectureException(LectureErrorCode.NOT_FOUND_LECTURE);

    public LectureException(DomainErrorCode errorCode) {
        super(errorCode);
    }
}
