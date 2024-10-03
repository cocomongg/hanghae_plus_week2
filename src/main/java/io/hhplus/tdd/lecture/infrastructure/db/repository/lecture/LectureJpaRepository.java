package io.hhplus.tdd.lecture.infrastructure.db.repository.lecture;

import io.hhplus.tdd.lecture.infrastructure.db.entity.lecture.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureJpaRepository extends JpaRepository<Lecture, Long> {

}
