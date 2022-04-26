package org.veritasopher.logic;

import lombok.Getter;

/**
 * Formula Type Enum
 *
 * @author Yepeng Ding
 */
public enum FormulaType {

    TRUE('\u22A4'), FALSE('\u22A5'), PROP('\u22A2'),
    NOT('\u00AC'), AND('\u2227'), OR('\u2228'), IMPLY('\u2192'),
    NEXT('\u25EF'), GLOBALLY('\u25A1'), FINALLY('\u25C7'),
    FORALL('\u2200'), EXISTS('\u2203');

    @Getter
    private final char value;

    FormulaType(char value) {
        this.value = value;
    }

}
