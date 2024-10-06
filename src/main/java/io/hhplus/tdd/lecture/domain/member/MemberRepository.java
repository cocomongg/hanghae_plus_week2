package io.hhplus.tdd.lecture.domain.member;

import io.hhplus.tdd.lecture.domain.member.model.MemberInfo;

public interface MemberRepository {
    MemberInfo getMember(Long memberId);
}
