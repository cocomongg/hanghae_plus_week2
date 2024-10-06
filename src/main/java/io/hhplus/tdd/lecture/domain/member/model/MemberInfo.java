package io.hhplus.tdd.lecture.domain.member.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberInfo {
    private final Long memberId;
    private final String name;
}
