package io.hhplus.tdd.lecture.application.lecture;

import static org.assertj.core.api.Assertions.assertThat;

import io.hhplus.tdd.lecture.domain.lecture.exception.LectureException;
import io.hhplus.tdd.lecture.infrastructure.db.entity.lecture.Lecture;
import io.hhplus.tdd.lecture.infrastructure.db.entity.lecture.LectureApplyHistory;
import io.hhplus.tdd.lecture.infrastructure.db.entity.lecture.LectureOption;
import io.hhplus.tdd.lecture.infrastructure.db.entity.member.Member;
import io.hhplus.tdd.lecture.infrastructure.db.repository.lecture.LectureApplyHistoryJpaRepository;
import io.hhplus.tdd.lecture.infrastructure.db.repository.lecture.LectureJpaRepository;
import io.hhplus.tdd.lecture.infrastructure.db.repository.lecture.LectureOptionJpaRepository;
import io.hhplus.tdd.lecture.infrastructure.db.repository.member.MemberJpaRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LectureFacadeConcurrencyTest {

    @Autowired
    private LectureJpaRepository lectureJpaRepository;

    @Autowired
    private LectureOptionJpaRepository lectureOptionJpaRepository;

    @Autowired
    private LectureApplyHistoryJpaRepository lectureApplyHistoryJpaRepository;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Autowired
    private LectureFacade lectureFacade;

    @BeforeEach
    public void setup() {
        lectureJpaRepository.deleteAllInBatch();
        lectureApplyHistoryJpaRepository.deleteAllInBatch();
        lectureOptionJpaRepository.deleteAllInBatch();
        memberJpaRepository.deleteAllInBatch();
    }

    @DisplayName("동시에 동일한 특강에 대해 40명이 신청했을 때, 30명만 성공한다.")
    @Test
    void should_AllowOnly30SuccessfulApplications_When_40ConcurrentApplications () throws InterruptedException {
        // given
        int attemptCount = 40;
        Lecture lecture = this.saveDefaultLecture();
        LectureOption lectureOption = this.saveDefaultLectureOption(lecture.getLectureId());
        List<Member> members = this.saveMembers(attemptCount);

        // when
        ExecutorService executorService = Executors.newFixedThreadPool(attemptCount);
        CountDownLatch latch = new CountDownLatch(attemptCount);

        for (int i = 0; i < attemptCount; i++) {
            Long memberId = members.get(i).getId();
            executorService.submit(() -> {
                try {
                    lectureFacade.applyLecture(memberId, lectureOption.getLectureOptionId());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        // then
        LectureOption findLectureOption =
            lectureOptionJpaRepository.findById(lectureOption.getLectureOptionId())
                .orElse(null);

        assertThat(findLectureOption).isNotNull();
        assertThat(findLectureOption.getCurrentApplyCount())
            .isEqualTo(findLectureOption.getMaxApplyCount());
    }

    @DisplayName("동일한 유저정보로 같은 특강을 5번 신청했을 때, 1번만 성공한다.")
    @Test
    void should_AllowOnly1Apply_When_SameUserApplies5Times() throws InterruptedException {
        // given
        int attemptCount = 5;
        Lecture lecture = this.saveDefaultLecture();
        LectureOption lectureOption = this.saveDefaultLectureOption(lecture.getLectureId());
        Member member = this.saveMembers(1).get(0);

        // when
        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failCount = new AtomicInteger();
        ExecutorService executorService = Executors.newFixedThreadPool(attemptCount);
        CountDownLatch latch = new CountDownLatch(attemptCount);

        for (int i = 0; i < attemptCount; i++) {
            executorService.submit(() -> {
                try {
                    lectureFacade.applyLecture(member.getId(), lectureOption.getLectureOptionId());
                    successCount.getAndIncrement();
                } catch (LectureException e) {
                    failCount.getAndIncrement();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        //then
        List<LectureApplyHistory> lectureApplyHistories =
            lectureApplyHistoryJpaRepository.findAllByMemberIdAndSuccessIsTrue(member.getId());

        assertThat(lectureApplyHistories).hasSize(1);
        assertThat(successCount.get()).isEqualTo(1);
        assertThat(failCount.get()).isEqualTo(attemptCount - 1);
    }


    private Lecture saveDefaultLecture() {
        return lectureJpaRepository.save(
            Lecture.builder()
                .title("title1")
                .description("desc1")
                .lecturerName("lecturer1")
                .createdAt(LocalDateTime.now())
                .build());
    }

    private LectureOption saveDefaultLectureOption(Long lectureId) {
        return lectureOptionJpaRepository.save(
            LectureOption.builder()
                .lectureId(lectureId)
                .applyStartDate(LocalDate.now())
                .applyEndDate(LocalDate.now())
                .maxApplyCount(30)
                .currentApplyCount(0)
                .createdAt(LocalDateTime.now())
                .build()
        );
    }

    private List<Member> saveMembers(int count) {
        List<Member> memberList = new ArrayList<>();

        for(int i = 0; i < count; ++i) {
            memberList.add(
                Member.builder()
                    .name("test-" + i)
                    .createdAt(LocalDateTime.now())
                    .build());
        }

        return memberJpaRepository.saveAll(memberList);
    }

}
