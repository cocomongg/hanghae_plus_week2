package io.hhplus.tdd.lecture.domain.lecture;

import io.hhplus.tdd.lecture.domain.lecture.model.LectureCommand.CreateApplyHistory;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureOptionInfo;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LectureService {

    private final LectureRepository lectureRepository;

    public LectureInfo getLecture(Long lectureId) {
        return lectureRepository.getLecture(lectureId);
    }

    public List<LectureInfo> getLectures(List<Long> lectureIds) {
        return lectureRepository.getLectures(lectureIds);
    }

    public Map<Long, List<LectureOptionInfo>> getLectureOptionMapByDate(LocalDate date) {
        return lectureRepository.getLectureOptionMapByDate(date);
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

    public boolean existsAppliedLectureHistory(Long memberId, Long lectureId) {
        return lectureRepository.existsAppliedLectureHistory(memberId, lectureId);
    }
}
