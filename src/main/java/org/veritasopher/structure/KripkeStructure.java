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

    @Getter
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
     * Get a state by its definition
     *
     * @param definition state definition
     * @return either a state or null
     */
    public Optional<State> getStateByDefinition(String definition) {
        return this.states.stream()
                .filter(s -> s.definition().equals(definition))
                .findAny();
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

    /**
     * Get an atomic proposition set of a given state by the labeling function
     *
     * @param state state
     * @return atomic proposition set where all elements hold in the given state
     */
    public Set<AtomicProposition> getAtomicPropositionSetOfState(State state) {
        return this.labeling.get(state);
    }

}
