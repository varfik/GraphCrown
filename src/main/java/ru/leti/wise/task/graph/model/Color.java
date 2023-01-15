package ru.leti.wise.task.graph.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Color {

    RED("red"),
    BLUE("blue"),
    GREEN("green");
    private final String value;
}
