package io.hhplus.tdd.lecture.infrastructure.db.lecture.repository;

import io.hhplus.tdd.lecture.infrastructure.db.lecture.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureJpaRepository extends JpaRepository<Lecture, Long>{

}
