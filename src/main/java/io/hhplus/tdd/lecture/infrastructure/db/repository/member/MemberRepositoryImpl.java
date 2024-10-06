package io.hhplus.tdd.lecture.infrastructure.db.repository.member;

import io.hhplus.tdd.lecture.domain.member.MemberRepository;
import io.hhplus.tdd.lecture.domain.member.exception.MemberException;
import io.hhplus.tdd.lecture.domain.member.model.MemberInfo;
import io.hhplus.tdd.lecture.infrastructure.db.entity.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MemberRepositoryImpl implements MemberRepository {
    private final MemberJpaRepository memberJpaRepository;

    @Override
    public MemberInfo getMember(Long memberId) {
        Member member = memberJpaRepository.findById(memberId)
            .orElseThrow(() -> MemberException.NOT_FOUND_MEMBER);

        return member.toMemberInfo();
    }
}
