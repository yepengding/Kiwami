package org.veritasopher.structure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.veritasopher.element.AtomicProposition;
import org.veritasopher.element.State;
import org.veritasopher.element.Transition;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Kripke Structure
 *
 * @author Yepeng Ding
 */
@AllArgsConstructor
public class KripkeStructure {

    private final Set<State> states;

    private final Set<State> initStates;

    private final Set<Transition> transitions;

    // Labeling function
    // Mapping from a state to a set of all atomic propositions holding in the state
    private final Map<State, Set<AtomicProposition>> labeling;

    public KripkeStructure() {
        this.states = new HashSet<>();
        this.initStates = new HashSet<>();
        this.transitions = new HashSet<>();
        this.labeling = new HashMap<>();
    }

    /**
     * Get Atomic Proposition Set of this Kripke Structure
     *
     * @return Atomic Proposition Set
     */
    public Set<AtomicProposition> getAtomicPropositionSet() {
        return this.labeling.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

}
