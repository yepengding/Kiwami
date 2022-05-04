package org.veritasopher.unit;

import org.junit.Test;
import org.veritasopher.element.AtomicProposition;
import org.veritasopher.logic.LTLFormula;

import static org.junit.Assert.assertEquals;
import static org.veritasopher.logic.LTLFormula.*;


/**
 * LTL Formula Test
 *
 * @author Yepeng Ding
 */
public class LTLFormulaTest {

    @Test
    public void testLTL0() {
        LTLFormula notCritical0 = Not(Prop(new AtomicProposition("critical0")));
        LTLFormula notCritical1 = Not(Prop(new AtomicProposition("critical1")));
        LTLFormula notSimultaneous = Or(notCritical0, notCritical1);
        LTLFormula alwaysNotSimultaneous = Globally(notSimultaneous);
        System.out.println(alwaysNotSimultaneous);
        assertEquals("□((¬(critical0)) ∨ (¬(critical1)))", alwaysNotSimultaneous.toString());
    }

    @Test
    public void testLTL1() {
        LTLFormula infinitelyOftenWait0 = Globally(Finally(Prop(new AtomicProposition("wait0"))));
        LTLFormula infinitelyOftenWait1 = Globally(Finally(Prop(new AtomicProposition("wait1"))));
        LTLFormula infinitelyOftenCritical0 = Globally(Finally(Prop(new AtomicProposition("critical0"))));
        LTLFormula infinitelyOftenCritical1 = Globally(Finally(Prop(new AtomicProposition("critical1"))));
        LTLFormula starvationFreedom = And(
                Imply(infinitelyOftenWait0, infinitelyOftenCritical0),
                Imply(infinitelyOftenWait1, infinitelyOftenCritical1)
        );
        System.out.println(starvationFreedom);
        assertEquals("((□(◇(wait0))) → (□(◇(critical0)))) ∧ ((□(◇(wait1))) → (□(◇(critical1))))", starvationFreedom.toString());
    }

}
