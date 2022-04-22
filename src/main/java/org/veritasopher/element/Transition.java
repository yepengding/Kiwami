package org.veritasopher.element;

public record Transition(
        State src,
        State dst
) {
}
