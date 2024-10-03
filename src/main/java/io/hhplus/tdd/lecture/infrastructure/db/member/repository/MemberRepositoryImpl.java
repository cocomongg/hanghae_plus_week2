package io.hhplus.tdd.lecture.infrastructure.db.member.repository;

import io.hhplus.tdd.lecture.domain.member.MemberRepository;
import io.hhplus.tdd.lecture.domain.member.exception.MemberException;
import io.hhplus.tdd.lecture.domain.member.model.MemberInfo;
import io.hhplus.tdd.lecture.infrastructure.db.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public MemberInfo getMember(Long memberId) {
        Member member = memberJpaRepository.findById(memberId)
            .orElseThrow(() -> MemberException.MEMBER_NOT_FOUND);

        return member.toMemberInfo();
    }
}
