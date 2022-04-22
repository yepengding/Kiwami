package org.veritasopher.element;

/**
 * Transition
 *
 * @param src source state
 * @param dst destination state
 * @author Yepeng Ding
 */
public record Transition(
        State src,
        State dst
) {
}
