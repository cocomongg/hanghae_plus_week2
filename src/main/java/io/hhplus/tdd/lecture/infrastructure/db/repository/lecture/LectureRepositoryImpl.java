package io.hhplus.tdd.lecture.infrastructure.db.repository.lecture;

import io.hhplus.tdd.lecture.domain.lecture.LectureRepository;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureOptionInfo;
import io.hhplus.tdd.lecture.infrastructure.db.entity.lecture.Lecture;
import io.hhplus.tdd.lecture.infrastructure.db.entity.lecture.LectureOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class LectureRepositoryImpl implements LectureRepository {

    private final LectureJpaRepository lectureJpaRepository;
    private final LectureOptionJpaRepository lectureOptionJpaRepository;

    @Override
    public List<LectureInfo> getLectures(List<Long> lectureIds) {
        List<Lecture> lectures = lectureJpaRepository.findAllById(lectureIds);

        return lectures.stream()
            .map(Lecture::toLectureInfo)
            .collect(Collectors.toList());
    }

    @Override
    public Map<Long, List<LectureOptionInfo>> getLectureOptionMapByDate(LocalDate date) {
        List<LectureOption> lectureOptions = lectureOptionJpaRepository.findAvailableOptions(
            date);

        return lectureOptions.stream()
            .map(LectureOption::toLectureOptionInfo)
            .collect(Collectors.groupingBy(LectureOptionInfo::getLectureId));
    }
}
