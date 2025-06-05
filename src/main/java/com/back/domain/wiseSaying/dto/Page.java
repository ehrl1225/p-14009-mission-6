package com.back.domain.wiseSaying.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class Page<T> extends Pageable {

    private final int totalCount;
    private final List<T> content;

    public Page(int totalCount, int page, int pageSize, List<T> content) {
        super(page, pageSize);
        this.totalCount = totalCount;
        this.content = content;

    }
}
