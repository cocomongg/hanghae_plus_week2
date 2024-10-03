package io.hhplus.tdd.lecture.domain.member;

import io.hhplus.tdd.lecture.domain.member.model.MemberInfo;
import org.springframework.stereotype.Repository;

public interface MemberRepository {
    MemberInfo getMember(Long memberId);
}
