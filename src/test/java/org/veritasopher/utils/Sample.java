package org.veritasopher.utils;

import org.veritasopher.element.AtomicProposition;
import org.veritasopher.element.State;
import org.veritasopher.element.Transition;
import org.veritasopher.structure.KripkeStructure;

import java.util.Map;
import java.util.Set;

/**
 * Sample Structures as Inputs
 */
public class Sample {

    public static KripkeStructure getMutualExclusionStructure() {
        // s0 () <-> s1 (critical0)
        // s0 () <-> s2 (critical1)
        // s2 (critical1) -> s3 (critical0, critical1)

        State s0 = new State("s0");
        State s1 = new State("s1");
        State s2 = new State("s2");
        State s3 = new State("s3");

        Transition t1 = new Transition(s0, s1);
        Transition t2 = new Transition(s1, s0);
        Transition t3 = new Transition(s0, s2);
        Transition t4 = new Transition(s2, s0);
        Transition t5 = new Transition(s2, s3);

        AtomicProposition critical0 = new AtomicProposition("critical0");
        AtomicProposition critical1 = new AtomicProposition("critical1");

        return new KripkeStructure(
                Set.of(s0, s1, s2, s3),
                Set.of(s0),
                Set.of(t1, t2, t3, t4, t5),
                Map.of(s0, Set.of(), s1, Set.of(critical0), s2, Set.of(critical1), s3, Set.of(critical0, critical1))
        );
    }

    public static KripkeStructure getKripkeStructure0() {
        // s1 (p1) -> s2 (p2) -> s3 (p3) -> s4 (p4) -> s5 (p5) -> s1 (p3)

        State s1 = new State("s1");
        State s2 = new State("s2");
        State s3 = new State("s3");
        State s4 = new State("s4");
        State s5 = new State("s5");

        Transition t1 = new Transition(s1, s2);
        Transition t2 = new Transition(s2, s3);
        Transition t3 = new Transition(s3, s4);
        Transition t4 = new Transition(s4, s5);
        Transition t5 = new Transition(s5, s1);

        AtomicProposition p1 = new AtomicProposition("p1");
        AtomicProposition p2 = new AtomicProposition("p2");
        AtomicProposition p3 = new AtomicProposition("p3");
        AtomicProposition p4 = new AtomicProposition("p4");
        AtomicProposition p5 = new AtomicProposition("p5");

        return new KripkeStructure(
                Set.of(s1, s2, s3, s4, s5),
                Set.of(s1),
                Set.of(t1, t2, t3, t4, t5),
                Map.of(s1, Set.of(p1), s2, Set.of(p2), s3, Set.of(p3), s4, Set.of(p4), s5, Set.of(p5))
        );
    }

}
