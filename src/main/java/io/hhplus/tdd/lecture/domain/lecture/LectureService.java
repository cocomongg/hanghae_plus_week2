package io.hhplus.tdd.lecture.domain.lecture;

import io.hhplus.tdd.lecture.domain.lecture.model.LectureCapacityInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureOptionInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LecturerInfo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LectureService {

    private final LectureRepository lectureRepository;

    public boolean existsLecture(Long lectureId) {
        return lectureRepository.existsLecture(lectureId);
    }

    public List<LectureInfo> getLectures() {
        return lectureRepository.getLectures();
    }

    public List<LecturerInfo> getLecturers(List<Long> lecturerIds) {
        return lectureRepository.getLecturers(lecturerIds);
    }

    public List<LectureOptionInfo> getLectureOptions(Long lectureId) {
        return lectureRepository.getLectureOptions(lectureId);
    }

    public List<LectureCapacityInfo> getLectureCapacities(List<Long> lectureOptionIds) {
        return lectureRepository.getLectureCapacities(lectureOptionIds);
    }
}
