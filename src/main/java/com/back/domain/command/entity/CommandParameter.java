package com.back.domain.command.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommandParameter {
    private final String key;
    private final String value;
}
