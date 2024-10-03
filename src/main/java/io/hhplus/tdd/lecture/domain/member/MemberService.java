package io.hhplus.tdd.lecture.domain.member;

import io.hhplus.tdd.lecture.domain.member.model.MemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberInfo getMember(Long memberId) {
        return memberRepository.getMember(memberId);
    }
}
