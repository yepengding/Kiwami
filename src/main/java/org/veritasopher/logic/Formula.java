package org.veritasopher.logic;

import org.veritasopher.element.AtomicProposition;
import org.veritasopher.exception.SystemException;

import static org.veritasopher.logic.FormulaType.*;

/**
 * Formula
 *
 * @param type  formula type
 * @param left  left formula operand
 * @param right right formula operand
 * @param prop  atomic proposition
 */
public record Formula(
        FormulaType type,
        Formula left,
        Formula right,
        AtomicProposition prop
) {

    public static Formula True() {
        return new Formula(TRUE, null, null, null);
    }

    public static Formula Prop(AtomicProposition proposition) {
        return new Formula(PROP, null, null, proposition);
    }

    public static Formula Not(Formula right) {
        return new Formula(NOT, null, right, null);
    }

    public static Formula And(Formula left, Formula right) {
        return new Formula(AND, left, right, null);
    }

    public static Formula Imply(Formula left, Formula right) {
        return new Formula(IMPLY, left, right, null);
    }

    public static Formula Or(Formula left, Formula right) {
        return new Formula(OR, left, right, null);
    }

    public static Formula Finally(Formula right) {
        return new Formula(FINALLY, null, right, null);
    }

    public static Formula Globally(Formula right) {
        return new Formula(GLOBALLY, null, right, null);
    }

    /**
     * Transform to a pretty string.
     *
     * @return pretty string
     */
    @Override
    public String toString() {
        switch (this.type) {
            case TRUE -> {
                return String.valueOf(TRUE.getValue());
            }
            case PROP -> {
                return prop.definition();
            }
            case NOT -> {
                return "%c(%s)".formatted(NOT.getValue(), this.right);
            }
            case AND -> {
                return "(%s) %c (%s)".formatted(this.left, AND.getValue(), this.right);
            }
            case OR -> {
                return "(%s) %c (%s)".formatted(this.left, OR.getValue(), this.right);
            }
            case IMPLY -> {
                return "(%s) %c (%s)".formatted(this.left, IMPLY.getValue(), this.right);
            }
            case GLOBALLY -> {
                return "%c(%s)".formatted(GLOBALLY.getValue(), this.right);
            }
            case FINALLY -> {
                return "%c(%s)".formatted(FINALLY.getValue(), this.right);
            }
            default -> {
                throw new SystemException("Unsupported formula type.");
            }
        }
    }
}
