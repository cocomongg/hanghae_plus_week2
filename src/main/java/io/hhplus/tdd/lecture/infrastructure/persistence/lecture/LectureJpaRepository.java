package io.hhplus.tdd.lecture.infrastructure.persistence.lecture;

import io.hhplus.tdd.lecture.domain.lecture.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureJpaRepository extends JpaRepository<Lecture, Long>{

}
