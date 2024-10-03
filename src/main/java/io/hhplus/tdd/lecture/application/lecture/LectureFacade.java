package io.hhplus.tdd.lecture.application.lecture;

import io.hhplus.tdd.lecture.domain.lecture.LectureMapper;
import io.hhplus.tdd.lecture.domain.lecture.LectureService;
import io.hhplus.tdd.lecture.domain.lecture.exception.LectureException;
import io.hhplus.tdd.lecture.domain.lecture.model.ApplyHistoryWithLecture;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureApplyHistoryInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureCommand.CreateApplyHistory;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureOptionInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureWithOption;
import io.hhplus.tdd.lecture.domain.member.MemberService;
import io.hhplus.tdd.lecture.domain.member.model.MemberInfo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LectureFacade {

    private final LectureService lectureService;
    private final MemberService memberService;
    private final LectureMapper lectureMapper;

    public List<LectureWithOption> getApplicableLectures(LocalDate date) {
        Map<Long, List<LectureOptionInfo>> lectureOptionMap =
            lectureService.getLectureOptionMapByDate(date);

        List<Long> lectureIds = new ArrayList<>(lectureOptionMap.keySet());
        List<LectureInfo> lectures = lectureService.getLectures(lectureIds);

        return lectureMapper.toLecturesWithOption(lectures, lectureOptionMap);
    }

    public LectureInfo applyLecture(Long memberId, Long lectureOptionId) {
        MemberInfo member = memberService.getMember(memberId);
        LectureOptionInfo lectureOption = lectureService.getLectureOption(lectureOptionId);
        LectureInfo lecture = lectureService.getLecture(lectureOption.getLectureId());
        boolean existsAppliedLectureHistory =
            lectureService.existsAppliedLectureHistory(member.getMemberId(), lecture.getLectureId());

        // 이미 신청한 강의인지 확인
        if(existsAppliedLectureHistory) {
            throw LectureException.ALREADY_APPLY_LECTURE;
        }

        // 현재 정원이 다 찬 강의인지 확인
        if(lectureOption.getMaxApplyCount() - lectureOption.getCurrentApplyCount() <= 0) {
            throw LectureException.EXCEED_MAX_CAPACITY;
        }

        lectureService.increaseCurrentApplyCapacity(lectureOption.getLectureOptionId());

        CreateApplyHistory createApplyHistory = CreateApplyHistory.builder()
            .memberId(member.getMemberId())
            .lectureId(lecture.getLectureId())
            .lectureOptionId(lectureOption.getLectureOptionId())
            .isSuccess(true)
            .appliedAt(LocalDateTime.now())
            .build();
        lectureService.saveApplyHistory(createApplyHistory);

        return lecture;
    }

    public List<ApplyHistoryWithLecture> getAppliedLectureHistories(Long memberId) {
        MemberInfo member = memberService.getMember(memberId);
        List<LectureApplyHistoryInfo> appliedLectureHistories =
            lectureService.getAppliedLectureHistories(member.getMemberId());

        List<Long> lectureIds = appliedLectureHistories.stream()
            .map(LectureApplyHistoryInfo::getLectureId)
            .toList();
        List<LectureInfo> lectures = lectureService.getLectures(lectureIds);

        return lectureMapper.toApplyHistoriesWithLecture(appliedLectureHistories, lectures);
    }
}
