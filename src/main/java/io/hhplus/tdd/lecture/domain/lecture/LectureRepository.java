package io.hhplus.tdd.lecture.domain.lecture;

import io.hhplus.tdd.lecture.domain.lecture.model.LectureInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureOptionInfo;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface LectureRepository {
    List<LectureInfo> getLectures(List<Long> lectureIds);

    Map<Long, List<LectureOptionInfo>> getLectureOptionMapByDate(LocalDate date);
}
