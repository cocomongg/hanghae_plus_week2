package io.hhplus.tdd.lecture.application.lecture;

import io.hhplus.tdd.lecture.domain.lecture.model.LectureCapacityInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureOptionInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LecturerInfo;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class LectureMapper {

    public List<LectureDto.LectureItem> toLectureItems(List<LectureInfo> lectureInfos,
        List<LecturerInfo> lecturerInfos) {

        Map<Long, LecturerInfo> lecturerInfoMap = lecturerInfos.stream()
            .collect(Collectors.toMap(
                LecturerInfo::getLecturerId,
                lecturerInfo -> lecturerInfo
            ));

        return lectureInfos.stream()
            .map(lectureInfo -> {
                LecturerInfo lecturerInfo = lecturerInfoMap.get(lectureInfo.getLecturerId());

                return LectureDto.LectureItem.builder()
                    .lectureId(lectureInfo.getLectureId())
                    .title(lectureInfo.getTitle())
                    .description(lectureInfo.getDescription())
                    .lecturerId(lecturerInfo.getLecturerId())
                    .lecturerName(lecturerInfo.getName())
                    .createdAt(lectureInfo.getCreatedAt())
                    .build();
            }).collect(Collectors.toList());
    }

    public List<LectureDto.LectureOptionItem> toLectureOptionItems(List<LectureOptionInfo> lectureOptionInfos,
        Map<Long, LectureCapacityInfo> lectureCapacityMap) {

        return lectureOptionInfos.stream()
            .map(lectureOptionInfo -> {
                LectureCapacityInfo lectureCapacityInfo =
                    lectureCapacityMap.get(lectureOptionInfo.getLectureOptionId());

                return LectureDto.LectureOptionItem.builder()
                    .lectureId(lectureOptionInfo.getLectureId())
                    .lectureOptionId(lectureOptionInfo.getLectureOptionId())
                    .currentApplyCount(lectureCapacityInfo.getCurrentApplyCount())
                    .maxApplyCount(lectureOptionInfo.getMaxApplyCount())
                    .lectureStartAt(lectureOptionInfo.getLectureStartAt())
                    .lectureEndAt(lectureOptionInfo.getLectureEndAt())
                    .build();
            })
            .collect(Collectors.toList());
    }
}
