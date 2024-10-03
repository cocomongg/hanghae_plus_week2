package io.hhplus.tdd.lecture.infrastructure.db.repository.lecture;

import io.hhplus.tdd.lecture.infrastructure.db.entity.lecture.LectureOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureOptionJpaRepository extends JpaRepository<LectureOption, Long> {

    @Query("SELECT lo FROM LectureOption lo WHERE lo.applyStartDate <= :date AND lo.applyEndDate >= :date")
    List<LectureOption> findAvailableOptions(@Param("date") LocalDate date);
}
