package com.back.domain.wiseSaying.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
public class WiseSaying {
    private final int id;
    @Setter
    private String content;
    @Setter
    private String author;


}
