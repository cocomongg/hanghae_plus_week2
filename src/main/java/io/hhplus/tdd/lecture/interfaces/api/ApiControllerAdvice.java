package io.hhplus.tdd.lecture.interfaces.api;

import io.hhplus.tdd.lecture.domain.common.exception.DomainErrorCode;
import io.hhplus.tdd.lecture.domain.common.exception.DomainException;
import io.hhplus.tdd.lecture.domain.common.exception.ErrorInfo;
import io.hhplus.tdd.lecture.interfaces.api.common.response.ApiErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
class ApiControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = DomainException.class)
    public ResponseEntity<ApiErrorResponse> handleDomainException(DomainException e) {
        DomainErrorCode errorCode = e.getErrorCode();
        ErrorInfo errorInfo = errorCode.getErrorInfo();

        return ResponseEntity
            .status(errorInfo.getStatus())
            .body(new ApiErrorResponse(errorInfo.getCode(), errorInfo.getMessage()));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception e) {
        return ResponseEntity.status(500).body(new ApiErrorResponse("500", "에러가 발생했습니다."));
    }
}
