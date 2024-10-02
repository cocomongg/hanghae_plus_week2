package io.hhplus.tdd.lecture.domain.lecture;

import io.hhplus.tdd.lecture.domain.lecture.model.LectureInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureOptionInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LecturerInfo;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository {

    List<LectureInfo> getLectures();
    Map<Long, LecturerInfo> getLecturerMap(List<Long> lecturerIds);

    Map<Long, List<LectureOptionInfo>> getLectureOptionMap(List<Long> lectureIds);
}
