package io.hhplus.tdd.lecture.domain.lecture;

import static java.util.stream.Collectors.groupingBy;

import io.hhplus.tdd.lecture.domain.lecture.model.ApplyHistoryWithLecture;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureApplyHistoryInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureOptionInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureWithOption;
import java.util.HashMap;
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

    public List<ApplyHistoryWithLecture> toApplyHistoriesWithLecture(
        List<LectureApplyHistoryInfo> appliedLectureHistories, List<LectureInfo> lectures) {

        Map<Long, LectureApplyHistoryInfo> lectureHistoryMap = new HashMap<>();

        for (LectureApplyHistoryInfo historyInfo : appliedLectureHistories) {
            lectureHistoryMap.put(historyInfo.getLectureId(), historyInfo);
        }

        return lectures.stream()
            .map(lecture ->  {
                LectureApplyHistoryInfo lectureApplyHistory = lectureHistoryMap.get(
                    lecture.getLectureId());

                return ApplyHistoryWithLecture.builder()
                    .lectureApplyHistoryId(lectureApplyHistory.getLectureApplyHistoryId())
                    .lectureId(lecture.getLectureId())
                    .lectureOptionId(lectureApplyHistory.getLectureOptionId())
                    .lectureTitle(lecture.getTitle())
                    .lectureDescription(lecture.getDescription())
                    .lecturerName(lecture.getLecturerName())
                    .appliedAt(lectureApplyHistory.getAppliedAt())
                    .build();
            })
            .collect(Collectors.toList());
    }
}
