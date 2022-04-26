package org.veritasopher.unit;

import org.junit.Test;
import org.veritasopher.element.AtomicProposition;
import org.veritasopher.logic.Formula;

import static org.veritasopher.logic.Formula.*;


/**
 * Formula Test
 *
 * @author Yepeng Ding
 */
public class FormulaTest {

    @Test
    public void testLTL0() {
        Formula notCritical0 = Not(Prop(new AtomicProposition("critical0")));
        Formula notCritical1 = Not(Prop(new AtomicProposition("critical1")));
        Formula notSimultaneous = Or(notCritical0, notCritical1);
        Formula alwaysNotSimultaneous = Globally(notSimultaneous);
        System.out.println(alwaysNotSimultaneous);
        assert "□((¬(critical0)) ∨ (¬(critical1)))".equals(alwaysNotSimultaneous.toString());
    }

    @Test
    public void testLTL1() {
        Formula infinitelyOftenWait0 = Globally(Finally(Prop(new AtomicProposition("wait0"))));
        Formula infinitelyOftenWait1 = Globally(Finally(Prop(new AtomicProposition("wait1"))));
        Formula infinitelyOftenCritical0 = Globally(Finally(Prop(new AtomicProposition("critical0"))));
        Formula infinitelyOftenCritical1 = Globally(Finally(Prop(new AtomicProposition("critical1"))));
        Formula starvationFreedom = And(
                Imply(infinitelyOftenWait0, infinitelyOftenCritical0),
                Imply(infinitelyOftenWait1, infinitelyOftenCritical1)
        );
        System.out.println(starvationFreedom);
        assert "((□(◇(wait0))) → (□(◇(critical0)))) ∧ ((□(◇(wait1))) → (□(◇(critical1))))".equals(starvationFreedom.toString());
    }

}
