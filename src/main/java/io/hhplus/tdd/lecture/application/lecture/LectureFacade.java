package io.hhplus.tdd.lecture.application.lecture;

import io.hhplus.tdd.lecture.application.lecture.LectureDto.LectureItem;
import io.hhplus.tdd.lecture.domain.lecture.LectureService;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureCapacityInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureOptionInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LecturerInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
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

        return lectureMapper.toLectureItems(lectures, lecturers);
    }

    public List<LectureDto.LectureOptionItem> getApplicableLectureOptions(Long lectureId) {
        boolean existsLecture = lectureService.existsLecture(lectureId);
        if(!existsLecture) {
            throw new RuntimeException();
        }

        List<LectureOptionInfo> lectureOptions = lectureService.getLectureOptions(lectureId);
        if(CollectionUtils.isEmpty(lectureOptions)) {
            return new ArrayList<>();
        }

        List<Long> lectureOptionIds = lectureOptions.stream()
            .map(LectureOptionInfo::getLectureOptionId)
            .collect(Collectors.toList());

        List<LectureCapacityInfo> lectureCapacities =
            lectureService.getLectureCapacities(lectureOptionIds);

        Map<Long, LectureCapacityInfo> lectureCapacityMap = lectureCapacities.stream()
            .collect(Collectors.toMap(LectureCapacityInfo::getLectureOptionId, Function.identity()));

        List<LectureOptionInfo> applicableLectureOptions = lectureOptions.stream()
            .filter(lectureOption -> {
                LectureCapacityInfo lectureCapacityInfo = lectureCapacityMap.get(lectureOption.getLectureOptionId());
                return lectureOption.isApplicable(lectureCapacityInfo.getCurrentApplyCount());
            })
            .collect(Collectors.toList());

        return lectureMapper.toLectureOptionItems(applicableLectureOptions, lectureCapacityMap);
    }
}
