package io.hhplus.tdd.lecture.infrastructure.db.repository.member;

import io.hhplus.tdd.lecture.infrastructure.db.entity.member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {

}
