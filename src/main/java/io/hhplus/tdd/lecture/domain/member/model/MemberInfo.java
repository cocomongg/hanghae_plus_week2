package io.hhplus.tdd.lecture.domain.member.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberInfo {
    private Long memberId;
    private String name;
    private LocalDateTime createdAt;
}
