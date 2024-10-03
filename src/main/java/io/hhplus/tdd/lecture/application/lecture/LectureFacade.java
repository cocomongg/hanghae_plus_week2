package io.hhplus.tdd.lecture.application.lecture;

import io.hhplus.tdd.lecture.application.lecture.LectureDto.LectureItem;
import io.hhplus.tdd.lecture.domain.lecture.LectureService;
import io.hhplus.tdd.lecture.domain.lecture.dto.LectureCommand.CreateApplyHistory;
import io.hhplus.tdd.lecture.domain.lecture.exception.LectureException;
import io.hhplus.tdd.lecture.domain.lecture.model.ApplyStatus;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureOptionInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LecturerInfo;
import io.hhplus.tdd.lecture.domain.member.MemberService;
import io.hhplus.tdd.lecture.domain.member.model.MemberInfo;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@RequiredArgsConstructor
@Component
public class LectureFacade {

    private final LectureService lectureService;

    private final MemberService memberService;

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
        lectureService.checkLectureExists(lectureId);

        List<LectureOptionInfo> lectureOptions = lectureService.getLectureOptions(lectureId);
        if(CollectionUtils.isEmpty(lectureOptions)) {
            return new ArrayList<>();
        }

        List<LectureOptionInfo> applicableLectureOptions = lectureOptions.stream()
            .filter(LectureOptionInfo::isApplicable)
            .collect(Collectors.toList());

        return lectureMapper.toLectureOptionItems(applicableLectureOptions);
    }

    // todo: step3, step4 적용
    @Transactional //todo: 제거는 어떻게 해야할까?
    public void applyLecture(Long memberId, Long lectureOptionId) {
        MemberInfo memberInfo = memberService.getMember(memberId);
        LectureOptionInfo lectureOption = lectureService.getLectureOption(lectureOptionId);
        lectureService.checkLectureExists(lectureOption.getLectureId());

        if(!lectureOption.isApplicable()) { // todo: validator로 빼야 하는가? pojo에는 로직이 들어가면 안되나?
            throw LectureException.NOT_POSSIBLE_APPLY;
        }

        lectureService.increaseCurrentApplyCapacity(lectureOption.getLectureOptionId());

        CreateApplyHistory createApplyHistoryCommand = CreateApplyHistory.builder()
            .memberId(memberInfo.getMemberId())
            .lectureOptionId(lectureOption.getLectureOptionId())
            .applyStatus(ApplyStatus.SUCCESS)
            .appliedAt(LocalDateTime.now())
            .build();
        lectureService.saveApplyHistory(createApplyHistoryCommand);
    }
}
