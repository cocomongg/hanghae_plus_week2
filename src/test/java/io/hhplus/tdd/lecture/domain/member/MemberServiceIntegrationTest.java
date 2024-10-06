package io.hhplus.tdd.lecture.domain.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.hhplus.tdd.lecture.domain.member.exception.MemberErrorCode;
import io.hhplus.tdd.lecture.domain.member.exception.MemberException;
import io.hhplus.tdd.lecture.domain.member.model.MemberInfo;
import io.hhplus.tdd.lecture.infrastructure.db.entity.member.Member;
import io.hhplus.tdd.lecture.infrastructure.db.repository.member.MemberJpaRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
class MemberServiceIntegrationTest {

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Autowired
    private MemberService memberService;

    @BeforeEach
    public void setup() {
        memberJpaRepository.deleteAll();
    }

    @DisplayName("member 조회 테스트")
    @Nested
    class GetMemberTest {
        @DisplayName("member가 존재하지 않을 때 MemberException이 발생한다.")
        @Test
        void should_ThrowMemberException_When_MemberNotFound() {
            // given
            Long memberId = 0L;

            // when, then
            assertThatThrownBy(() -> memberService.getMember(memberId))
                .isInstanceOf(MemberException.class)
                .hasMessage(MemberErrorCode.NOT_FOUND_MEMBER.getMessage());
        }

        @DisplayName("member가 존재하면 memberInfo를 반환한다.")
        @Test
        void should_ReturnMemberInfo_When_MemberFound() {
            // given
            Member member = memberJpaRepository.save(
                Member.builder()
                    .name("name")
                    .createdAt(LocalDateTime.now())
                    .build()
            );

            // when
            MemberInfo memberInfo = memberService.getMember(member.getId());

            // then
            assertThat(memberInfo.getMemberId()).isEqualTo(member.getId());
            assertThat(memberInfo.getName()).isEqualTo(member.getName());
        }
    }
}