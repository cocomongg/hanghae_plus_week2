package io.hhplus.tdd.lecture.application.lecture;

import io.hhplus.tdd.lecture.application.lecture.LectureDto.LectureItem;
import io.hhplus.tdd.lecture.domain.lecture.LectureService;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LecturerInfo;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@RequiredArgsConstructor
@Component
public class LectureFacade {

    private final LectureService lectureService;

    private final LectureMapper lectureMapper;

    public List<LectureItem> getLectures() {
        List<LectureInfo> lectures = lectureService.getLectures();
        if(CollectionUtils.isEmpty(lectures)) {
            return new ArrayList<>();
        }

        List<Long> lecturerIds = new ArrayList<>();
        lectures.forEach(lectureInfo -> {
            lecturerIds.add(lectureInfo.getLecturerId());
        });

        List<LecturerInfo> lecturers = lectureService.getLecturers(lecturerIds);

        return lectureMapper.toLectureItem(lectures, lecturers);
    }
}
