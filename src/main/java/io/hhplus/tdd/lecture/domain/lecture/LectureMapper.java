package io.hhplus.tdd.lecture.domain.lecture;

import io.hhplus.tdd.lecture.domain.lecture.model.LectureInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureOptionInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureWithOption;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class LectureMapper {

    public List<LectureWithOption> toLecturesWithOption(List<LectureInfo> lectures,
        Map<Long, List<LectureOptionInfo>> lectureOptionMap) {
        return lectures.stream()
            .map(lectureInfo -> {
                Long lectureId = lectureInfo.getLectureId();
                List<LectureOptionInfo> lectureOptionInfos = lectureOptionMap.get(lectureId);

                return LectureWithOption.builder()
                    .lectureInfo(lectureInfo)
                    .lectureOptionInfos(lectureOptionInfos)
                    .build();
            })
            .collect(Collectors.toList());
    }
}
