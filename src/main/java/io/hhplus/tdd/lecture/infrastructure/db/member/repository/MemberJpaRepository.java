package io.hhplus.tdd.lecture.infrastructure.db.member.repository;

import io.hhplus.tdd.lecture.infrastructure.db.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberJpaRepository extends JpaRepository<Member, Long> {

}
