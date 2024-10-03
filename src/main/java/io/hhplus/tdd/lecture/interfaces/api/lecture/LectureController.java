package io.hhplus.tdd.lecture.interfaces.api.lecture;

import io.hhplus.tdd.lecture.application.lecture.LectureFacade;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureInfo;
import io.hhplus.tdd.lecture.domain.lecture.model.LectureWithOption;
import io.hhplus.tdd.lecture.interfaces.api.common.response.ApiResponse;
import io.hhplus.tdd.lecture.interfaces.api.lecture.LectureRequest.ApplyLecture;
import io.hhplus.tdd.lecture.interfaces.api.lecture.LectureRequest.GetApplicableLectures;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/lectures")
@RestController
public class LectureController {

    private final LectureFacade lectureFacade;

    // 1. 해당 날짜에 신청가능한 특강 목록 조회 api
    @GetMapping("/to-apply")
    public ApiResponse<List<LectureWithOption>> getApplicableLectures(@Valid @ModelAttribute GetApplicableLectures req) {
        return ApiResponse.OK(lectureFacade.getApplicableLectures(req.getDate()));
    }

    // 2. 특강 신청 api
    @PostMapping("/apply")
    public ApiResponse<LectureInfo> applyLecture(@Valid @RequestBody ApplyLecture req) {
        return ApiResponse.OK(lectureFacade.applyLecture(req.getMemberId(), req.getLectureOptionId()));
    }

}
