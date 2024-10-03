package io.hhplus.tdd.lecture.domain.member.exception;

import io.hhplus.tdd.lecture.domain.common.exception.DomainErrorCode;
import io.hhplus.tdd.lecture.domain.common.exception.DomainException;

public class MemberException extends DomainException {

    public static final MemberException MEMBER_NOT_FOUND =
        new MemberException(MemberErrorCode.NOT_FOUND_MEMBER);

    public MemberException(DomainErrorCode errorCode) {
        super(errorCode);
    }
}
