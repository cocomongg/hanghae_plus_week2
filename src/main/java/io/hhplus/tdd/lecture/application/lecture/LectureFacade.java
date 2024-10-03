package io.hhplus.tdd.lecture.application.lecture;

import io.hhplus.tdd.lecture.domain.lecture.LectureMapper;
import io.hhplus.tdd.lecture.domain.lecture.LectureService;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureOptionInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureWithOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LectureFacade {

    private final LectureService lectureService;
    private final LectureMapper lectureMapper;

    public List<LectureWithOption> getApplicableLectures(LocalDate date) {
        Map<Long, List<LectureOptionInfo>> lectureOptionMap =
            lectureService.getLectureOptionMapByDate(date);

        List<Long> lectureIds = new ArrayList<>(lectureOptionMap.keySet());
        List<LectureInfo> lectures = lectureService.getLectures(lectureIds);

        return lectureMapper.toLecturesWithOption(lectures, lectureOptionMap);
    }
}
