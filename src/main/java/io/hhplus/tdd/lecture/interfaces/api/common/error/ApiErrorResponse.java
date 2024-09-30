package io.hhplus.tdd.lecture.interfaces.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiErrorResponse {
    private final String code;
    private final String message;
}
