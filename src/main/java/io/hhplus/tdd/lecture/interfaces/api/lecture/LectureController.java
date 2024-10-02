package io.hhplus.tdd.lecture.interfaces.api.lecture;

import io.hhplus.tdd.lecture.application.lecture.LectureDto.LectureItem;
import io.hhplus.tdd.lecture.application.lecture.LectureFacade;
import io.hhplus.tdd.lecture.interfaces.api.common.response.ApiResponse;
import io.hhplus.tdd.lecture.interfaces.api.common.response.PageResponse;
import io.hhplus.tdd.lecture.interfaces.api.lecture.LectureRequest.ApplyLecture;
import io.hhplus.tdd.lecture.interfaces.api.lecture.LectureResponse.GetApplicableLectures;
import io.hhplus.tdd.lecture.interfaces.api.lecture.LectureResponse.GetLectureApplyHistories;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/lectures")
@RestController
public class LectureController {

    private final LectureFacade lectureFacade;

    @GetMapping("")
    public ApiResponse<List<LectureItem>> getLectures() {
        return ApiResponse.OK(lectureFacade.getLectures());
    }

    @GetMapping("/options/applicable")
    public ApiResponse<PageResponse<GetApplicableLectures>> getApplicableLectures(LectureRequest.GetApplicableLectures req) {
        PageResponse<GetApplicableLectures> getApplicableLecturesPageResponse = new PageResponse<>(
            0, 0, 0, List.of(new LectureResponse.GetApplicableLectures()));

        return ApiResponse.OK(getApplicableLecturesPageResponse);
    }

    @GetMapping("/apply-histories")
    public ApiResponse<PageResponse<GetLectureApplyHistories>> getLectureApplyHistories(LectureRequest.GetApplicableLectures req) {
        PageResponse<GetLectureApplyHistories> getLectureApplyHistoriesPageResponse = new PageResponse<>(
            0, 0, 0, List.of(new LectureResponse.GetLectureApplyHistories()));

        return ApiResponse.OK(getLectureApplyHistoriesPageResponse);
    }

    @PostMapping("/options/apply")
    public ApiResponse<?> applyLecture(@RequestBody ApplyLecture req) {
        return ApiResponse.OK();
    }
}
