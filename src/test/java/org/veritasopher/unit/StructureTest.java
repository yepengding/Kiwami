package org.veritasopher.unit;

import org.junit.Test;
import org.veritasopher.element.AtomicProposition;
import org.veritasopher.element.State;
import org.veritasopher.element.Transition;
import org.veritasopher.structure.KripkeStructure;

import java.util.Map;
import java.util.Set;

public class StructureTest {

    @Test
    public void testKripkeStructure() {

        // s1 (p1) -> s2 (p2) -> s3 (p3) -> s4 (p4) -> s5 (p5) -> s1 (p3)
        AtomicProposition p1 = new AtomicProposition("p1");
        AtomicProposition p2 = new AtomicProposition("p2");
        AtomicProposition p3 = new AtomicProposition("p3");
        AtomicProposition p4 = new AtomicProposition("p4");
        AtomicProposition p5 = new AtomicProposition("p5");

        State s1 = new State(Set.of(p1));
        State s2 = new State(Set.of(p2));
        State s3 = new State(Set.of(p3));
        State s4 = new State(Set.of(p4));
        State s5 = new State(Set.of(p5));

        Transition t1 = new Transition(s1, s2);
        Transition t2 = new Transition(s2, s3);
        Transition t3 = new Transition(s3, s4);
        Transition t4 = new Transition(s4, s5);
        Transition t5 = new Transition(s5, s1);

        KripkeStructure kripkeStructure = new KripkeStructure(
                Map.of(1, s1, 2, s2, 3, s3, 4, s4, 5, s5),
                Set.of(1),
                Set.of(t1, t2, t3, t4, t5)
        );

        System.out.println(kripkeStructure);
    }

}
