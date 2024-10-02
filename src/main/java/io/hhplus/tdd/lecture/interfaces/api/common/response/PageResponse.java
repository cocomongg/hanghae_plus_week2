package io.hhplus.tdd.lecture.interfaces.api.common.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class PageResponse<T> {
    private final long totalCount;
    private final int totalPages;
    private final int currentPage;
    private final List<T> contents;
}
