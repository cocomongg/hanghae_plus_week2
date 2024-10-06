package io.hhplus.tdd.lecture.infrastructure.db.repository.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import io.hhplus.tdd.lecture.domain.member.exception.MemberErrorCode;
import io.hhplus.tdd.lecture.domain.member.exception.MemberException;
import io.hhplus.tdd.lecture.domain.member.model.MemberInfo;
import io.hhplus.tdd.lecture.infrastructure.db.entity.member.Member;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberRepositoryImplTest {

    @Mock
    private MemberJpaRepository memberJpaRepository;

    @InjectMocks
    private MemberRepositoryImpl memberRepositoryImpl;

    @DisplayName("member 조회 테스트")
    @Nested
    class GetMemberTest {
        @DisplayName("memberId에 해당하는 member가 없으면 MemberException이 발생한다.")
        @Test
        void should_ThrowMemberException_When_NotFoundByMemberId() {
            // given
            Long memberId = 0L;

            when(memberJpaRepository.findById(memberId))
                .thenReturn(Optional.empty());

            // when, then
            assertThatThrownBy(() -> memberRepositoryImpl.getMember(memberId))
                .isInstanceOf(MemberException.class)
                .hasMessage(MemberErrorCode.NOT_FOUND_MEMBER.getMessage());
        }

        @DisplayName("memberId에 해당하는 member를 memberInfo로 변환하여 반환한다.")
        @Test
        void should_ReturnMemberInfo_When_FoundMember() {
            // given
            Long memberId = 1L;
            Member member = Member.builder()
                .id(memberId)
                .name("name")
                .build();

            when(memberJpaRepository.findById(memberId))
                .thenReturn(Optional.of(member));

            // when
            MemberInfo memberInfo = memberRepositoryImpl.getMember(memberId);

            // then
            assertThat(memberInfo.getMemberId()).isEqualTo(member.getId());
            assertThat(memberInfo.getName()).isEqualTo(member.getName());
        }
    }

}