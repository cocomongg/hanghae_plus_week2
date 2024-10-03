package io.hhplus.tdd.lecture.infrastructure.db.repository.lecture;

import io.hhplus.tdd.lecture.domain.lecture.LectureRepository;
import io.hhplus.tdd.lecture.domain.lecture.exception.LectureException;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureApplyHistoryInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureCommand.CreateApplyHistory;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureOptionInfo;
import io.hhplus.tdd.lecture.infrastructure.db.entity.lecture.Lecture;
import io.hhplus.tdd.lecture.infrastructure.db.entity.lecture.LectureApplyHistory;
import io.hhplus.tdd.lecture.infrastructure.db.entity.lecture.LectureOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
public class LectureRepositoryImpl implements LectureRepository {

    private final LectureJpaRepository lectureJpaRepository;
    private final LectureOptionJpaRepository lectureOptionJpaRepository;
    private final LectureApplyHistoryJpaRepository lectureApplyHistoryJpaRepository;

    @Override
    public List<LectureInfo> getLectures(List<Long> lectureIds) {
        List<Lecture> lectures = lectureJpaRepository.findAllById(lectureIds);

        return lectures.stream()
            .map(Lecture::toLectureInfo)
            .collect(Collectors.toList());
    }

    @Override
    public Map<Long, List<LectureOptionInfo>> getLectureOptionMapByDate(LocalDate date) {
        List<LectureOption> lectureOptions = lectureOptionJpaRepository.findAvailableOptions(
            date);

        return lectureOptions.stream()
            .map(LectureOption::toLectureOptionInfo)
            .collect(Collectors.groupingBy(LectureOptionInfo::getLectureId));
    }

    @Override
    public LectureInfo getLecture(Long lectureId) {
        Lecture lecture = lectureJpaRepository.findById(lectureId)
            .orElseThrow(() -> LectureException.NOT_FOUND_LECTURE);

        return lecture.toLectureInfo();
    }

    @Override
    public LectureOptionInfo getLectureOption(Long lectureOptionId) {
        LectureOption lectureOption = lectureOptionJpaRepository.findById(lectureOptionId)
            .orElseThrow(() -> LectureException.NOT_FOUND_LECTURE_OPTION);

        return lectureOption.toLectureOptionInfo();
    }

    @Transactional
    @Override
    public LectureOptionInfo increaseCurrentApplyCapacity(Long lectureOptionId) {
        LectureOption lectureOption = lectureOptionJpaRepository.findById(lectureOptionId)
            .orElseThrow(() -> LectureException.NOT_FOUND_LECTURE_OPTION);

        lectureOption.increaseCurrentApplyCount();
        return lectureOption.toLectureOptionInfo();
    }

    @Override
    public void saveApplyHistory(CreateApplyHistory command) {
        LectureApplyHistory lectureApplyHistory = LectureApplyHistory.builder()
            .memberId(command.getMemberId())
            .lectureId(command.getLectureId())
            .lectureOptionId(command.getLectureOptionId())
            .success(command.isSuccess())
            .appliedAt(command.getAppliedAt())
            .createdAt(LocalDateTime.now())
            .build();

        lectureApplyHistoryJpaRepository.save(lectureApplyHistory);
    }

    @Override
    public boolean existsAppliedLectureHistory(Long memberId, Long lectureId) {
        return lectureApplyHistoryJpaRepository
            .existsByMemberIdAndLectureIdAndSuccessIsTrue(memberId, lectureId);
    }

    @Override
    public List<LectureApplyHistoryInfo> getAppliedLectureHistories(Long memberId) {
        List<LectureApplyHistory> lectureApplyHistories =
            lectureApplyHistoryJpaRepository.findAllByMemberIdAndSuccessIsTrue(memberId);

        return lectureApplyHistories.stream()
            .map(LectureApplyHistory::toLectureApplyHistoryInfo)
            .collect(Collectors.toList());
    }
}
