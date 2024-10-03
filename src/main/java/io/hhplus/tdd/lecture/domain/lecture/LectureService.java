package io.hhplus.tdd.lecture.domain.lecture;

import io.hhplus.tdd.lecture.domain.lecture.dto.LectureCommand.CreateApplyHistory;
import io.hhplus.tdd.lecture.domain.lecture.exception.LectureException;
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

    public void checkLectureExists(Long lectureId) {
        boolean existsLecture = lectureRepository.existsLecture(lectureId);
        if(!existsLecture) {
            throw LectureException.NOT_FOUND_LECTURE;
        }
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

    public LectureOptionInfo getLectureOption(Long lectureOptionId) {
        return lectureRepository.getLectureOption(lectureOptionId);
    }

    public void increaseCurrentApplyCapacity(Long lectureOptionId) {
        lectureRepository.increaseCurrentApplyCapacity(lectureOptionId);
    }

    public void saveApplyHistory(CreateApplyHistory command) {
        lectureRepository.saveApplyHistory(command);
    }
}
