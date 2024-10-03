package io.hhplus.tdd.lecture.domain.lecture;

import io.hhplus.tdd.lecture.domain.lecture.model.LectureInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureOptionInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LecturerInfo;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository {

    boolean existsLecture(Long lectureId);

    List<LectureInfo> getLectures();

    List<LecturerInfo> getLecturers(List<Long> lecturerIds);

    List<LectureOptionInfo> getLectureOptions(Long lectureId);
}
