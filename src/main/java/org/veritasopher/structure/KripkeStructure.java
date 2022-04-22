package org.veritasopher.structure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.veritasopher.element.AtomicProposition;
import org.veritasopher.element.State;
import org.veritasopher.element.Transition;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@Getter
public class KripkeStructure {

    private final Map<Integer, State> states;

    private final Set<Integer> initStates;

    private final Set<Transition> transitions;

    public KripkeStructure() {
        this.states = new HashMap<>();
        this.initStates = new HashSet<>();
        this.transitions = new HashSet<>();
    }

}
