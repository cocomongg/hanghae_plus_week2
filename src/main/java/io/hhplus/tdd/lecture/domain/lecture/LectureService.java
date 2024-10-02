package io.hhplus.tdd.lecture.domain.lecture;

import io.hhplus.tdd.lecture.domain.lecture.model.LectureInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureOptionInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LecturerInfo;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LectureService {

    private final LectureRepository lectureRepository;

    public List<LectureInfo> getLectures() {
        return lectureRepository.getLectures();
    }

    public Map<Long, LecturerInfo> getLecturers(List<Long> lecturerIds) {
        return lectureRepository.getLecturerMap(lecturerIds);
    }

    public Map<Long, List<LectureOptionInfo>> getLectureOptions(List<Long> lectureIds) {
        return lectureRepository.getLectureOptionMap(lectureIds);
    }
}
