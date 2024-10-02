package io.hhplus.tdd.lecture.infrastructure.db.lecture.repository.impl;

import io.hhplus.tdd.lecture.domain.lecture.LectureRepository;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureOptionInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LecturerInfo;
import io.hhplus.tdd.lecture.infrastructure.db.lecture.entity.Lecture;
import io.hhplus.tdd.lecture.infrastructure.db.lecture.entity.LectureOption;
import io.hhplus.tdd.lecture.infrastructure.db.lecture.entity.Lecturer;
import io.hhplus.tdd.lecture.infrastructure.db.lecture.repository.LectureJpaRepository;
import io.hhplus.tdd.lecture.infrastructure.db.lecture.repository.LectureOptionJpaRepository;
import io.hhplus.tdd.lecture.infrastructure.db.lecture.repository.LecturerJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@RequiredArgsConstructor
@Repository
public class LectureRepositoryImpl implements LectureRepository {

    private final LectureJpaRepository lectureJpaRepository;
    private final LecturerJpaRepository lecturerJpaRepository;
    private final LectureOptionJpaRepository lectureOptionJpaRepository;

    @Override
    public List<LectureInfo> getLectures() {
        List<Lecture> lectures = lectureJpaRepository.findAll();

        if(CollectionUtils.isEmpty(lectures)) {
            throw new EntityNotFoundException("Lecturers not found");
        }

        return lectures.stream()
            .map(Lecture::toLecturerInfo)
            .collect(Collectors.toList());
    }

    @Override
    public Map<Long, LecturerInfo> getLecturerMap(List<Long> lecturerIds) {
        List<Lecturer> lecturers = lecturerJpaRepository.findAllById(lecturerIds);

        if(CollectionUtils.isEmpty(lecturers)) {
            throw new EntityNotFoundException("Lecturers not found");
        }

        return lecturers.stream()
            .collect(Collectors.toMap(
                Lecturer::getLecturerId,
                Lecturer::toLecturerInfo
            ));
    }

    @Override
    public Map<Long, List<LectureOptionInfo>> getLectureOptionMap(List<Long> lectureIds) {
        List<LectureOption> lectureOptions = lectureOptionJpaRepository
            .findAllByLectureIdIn(lectureIds);

        if(CollectionUtils.isEmpty(lectureOptions)) {
            throw new EntityNotFoundException("LectureOptions not found");
        }

        return lectureOptions.stream()
            .map(LectureOption::toLectureOptionInfo)
            .collect(Collectors.groupingBy(
                LectureOptionInfo::getLectureId
            ));
    }
}
