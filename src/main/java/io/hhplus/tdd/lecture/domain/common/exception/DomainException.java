package io.hhplus.tdd.lecture.domain.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class DomainException extends RuntimeException{
    private final DomainErrorCode errorCode;

    public DomainException(DomainErrorCode errorCode) {
        super(errorCode.getErrorInfo().getMessage());
        this.errorCode = errorCode;
    }
}
