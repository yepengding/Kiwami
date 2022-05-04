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
public record LTLFormula(
        FormulaType type,
        LTLFormula left,
        LTLFormula right,
        AtomicProposition prop
) {

    public static LTLFormula True() {
        return new LTLFormula(TRUE, null, null, null);
    }

    public static LTLFormula Prop(AtomicProposition proposition) {
        return new LTLFormula(PROP, null, null, proposition);
    }

    public static LTLFormula Not(LTLFormula right) {
        return new LTLFormula(NOT, null, right, null);
    }

    public static LTLFormula And(LTLFormula left, LTLFormula right) {
        return new LTLFormula(AND, left, right, null);
    }

    public static LTLFormula Imply(LTLFormula left, LTLFormula right) {
        return new LTLFormula(IMPLY, left, right, null);
    }

    public static LTLFormula Or(LTLFormula left, LTLFormula right) {
        return new LTLFormula(OR, left, right, null);
    }

    public static LTLFormula Finally(LTLFormula right) {
        return new LTLFormula(FINALLY, null, right, null);
    }

    public static LTLFormula Globally(LTLFormula right) {
        return new LTLFormula(GLOBALLY, null, right, null);
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
            default -> throw new SystemException("Unsupported formula type.");
        }
    }
}
