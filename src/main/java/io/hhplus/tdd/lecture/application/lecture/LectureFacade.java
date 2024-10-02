package io.hhplus.tdd.lecture.application.lecture;

import io.hhplus.tdd.lecture.domain.lecture.LectureService;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureOptionInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LecturerInfo;
import io.hhplus.tdd.lecture.interfaces.api.lecture.LectureResponse;
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

    public List<LectureResponse.GetLectures> getLectures() {
        List<LectureInfo> lectures = lectureService.getLectures();

        List<Long> lectureIds = new ArrayList<>();
        List<Long> lecturerIds = new ArrayList<>();
        lectures.forEach(lectureInfo -> {
            lectureIds.add(lectureInfo.getLectureId());
            lecturerIds.add(lectureInfo.getLecturerId());
        });

        Map<Long, List<LectureOptionInfo>> lectureOptionMap = lectureService.getLectureOptions(lectureIds);
        Map<Long, LecturerInfo> lecturerMap = lectureService.getLecturers(lecturerIds);

        return lectures.stream()
            .map(lectureInfo -> {
                LecturerInfo lecturerInfo = lecturerMap.get(lectureInfo.getLecturerId());
                List<LectureOptionInfo> lectureOptionInfos = lectureOptionMap.get(lectureInfo.getLectureId());
                List<Long> lectureOptionIds = lectureOptionInfos.stream()
                    .map(LectureOptionInfo::getLectureOptionId)
                    .toList();

                return LectureResponse.GetLectures.builder()
                    .lectureId(lectureInfo.getLectureId())
                    .title(lectureInfo.getTitle())
                    .description(lectureInfo.getDescription())
                    .lectureOptionIds(lectureOptionIds)
                    .lecturerId(lecturerInfo.getLecturerId())
                    .lecturerName(lecturerInfo.getName())
                    .createdAt(lectureInfo.getCreatedAt())
                    .build();
            })
            .collect(Collectors.toList());
    }
}
