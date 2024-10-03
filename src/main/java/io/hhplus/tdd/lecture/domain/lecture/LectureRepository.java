package io.hhplus.tdd.lecture.domain.lecture;

import io.hhplus.tdd.lecture.domain.lecture.dto.LectureCommand.CreateApplyHistory;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureOptionInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LecturerInfo;
import java.util.List;

public interface LectureRepository {

    boolean existsLecture(Long lectureId);

    List<LectureInfo> getLectures();

    List<LecturerInfo> getLecturers(List<Long> lecturerIds);

    List<LectureOptionInfo> getLectureOptions(Long lectureId);

    LectureOptionInfo getLectureOption(Long lectureOptionId);

    LectureOptionInfo increaseCurrentApplyCapacity(Long lectureOptionId);

    void saveApplyHistory(CreateApplyHistory command);
}
