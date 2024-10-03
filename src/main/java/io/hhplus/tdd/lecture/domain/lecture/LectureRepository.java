package io.hhplus.tdd.lecture.domain.lecture;

import io.hhplus.tdd.lecture.domain.lecture.model.LectureApplyHistoryInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureApplyHistoryInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureCommand.CreateApplyHistory;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureOptionInfo;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface LectureRepository {
    List<LectureInfo> getLectures(List<Long> lectureIds);

    Map<Long, List<LectureOptionInfo>> getLectureOptionMapByDate(LocalDate date);

    LectureInfo getLecture(Long lectureId);

    LectureOptionInfo getLectureOption(Long lectureOptionId);

    LectureOptionInfo increaseCurrentApplyCapacity(Long lectureOptionId);

    LectureApplyHistoryInfo saveApplyHistory(CreateApplyHistory command);

    boolean existsAppliedLectureHistory(Long memberId, Long lectureId);

    List<LectureApplyHistoryInfo> getAppliedLectureHistories(Long memberId);
}
