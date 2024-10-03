package io.hhplus.tdd.lecture.infrastructure.db.repository.member;

import io.hhplus.tdd.lecture.infrastructure.db.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

}
