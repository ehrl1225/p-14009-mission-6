package com.back.domain.wiseSaying.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Pageable {
    private int page;
    private int pageSize;
}
