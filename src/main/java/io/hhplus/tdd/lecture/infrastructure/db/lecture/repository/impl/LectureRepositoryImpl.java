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
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class LectureRepositoryImpl implements LectureRepository {

    private final LectureJpaRepository lectureJpaRepository;
    private final LecturerJpaRepository lecturerJpaRepository;
    private final LectureOptionJpaRepository lectureOptionJpaRepository;

    @Override
    public boolean existsLecture(Long lectureId) {
        return lectureJpaRepository.existsById(lectureId);
    }

    @Override
    public List<LectureInfo> getLectures() {
        List<Lecture> lectures = lectureJpaRepository.findAll();

        return lectures.stream()
            .map(Lecture::toLecturerInfo)
            .collect(Collectors.toList());
    }

    @Override
    public List<LecturerInfo> getLecturers(List<Long> lecturerIds) {
        List<Lecturer> lecturers = lecturerJpaRepository.findAllById(lecturerIds);

        return lecturers.stream()
            .map(Lecturer::toLecturerInfo)
            .collect(Collectors.toList());
    }

    @Override
    public List<LectureOptionInfo> getLectureOptions(Long lectureId) {
        List<LectureOption> lectureOptions =
            lectureOptionJpaRepository.findAllByLectureId(lectureId);

        return lectureOptions.stream()
            .map(LectureOption::toLectureOptionInfo)
            .collect(Collectors.toList());
    }
}
