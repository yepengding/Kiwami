package org.veritasopher.structure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.veritasopher.element.AtomicProposition;
import org.veritasopher.element.State;
import org.veritasopher.element.Transition;
import org.veritasopher.exception.Assert;
import org.veritasopher.exception.SystemException;

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

    @Getter
    private final Set<State> initStates;

    @Getter
    private final Set<Transition> transitions;

    /**
     * Labeling function.
     * Map: state -> a set of all atomic propositions holding in the state
     */
    private final Map<State, Set<AtomicProposition>> labeling;

    public KripkeStructure() {
        this.states = new HashSet<>();
        this.initStates = new HashSet<>();
        this.transitions = new HashSet<>();
        this.labeling = new HashMap<>();
    }

    /**
     * Check the legacy of a Kripke structure.
     */
    public void checkLegacy() {
        // Initial state set is not empty
        Assert.isTrue(this.initStates.size() > 0,
                new SystemException("The initial state set is empty."));

        // State set is not empty
        Set<State> stateSet = this.states;
        Assert.isTrue(stateSet.size() > 0,
                new SystemException("The state set is empty."));

        // Initial states are in the state set
        this.initStates
                .forEach(s ->
                        Assert.isTrue(stateSet.contains(s),
                                new SystemException("Initial state (%s) is not defined in the state set.".formatted(s.definition())))
                );

        // Transition states are in the state set
        this.transitions
                .forEach(t -> {
                    Assert.isTrue(stateSet.contains(t.src()),
                            new SystemException("State (%s) is not defined in the state set.".formatted(t.src().definition())));
                    Assert.isTrue(stateSet.contains(t.dst()),
                            new SystemException("State (%s) is not defined in the state set.".formatted(t.dst().definition())));
                });
    }

    /**
     * Get a state by its definition.
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
     * Get Atomic Proposition Set of this Kripke Structure.
     *
     * @return Atomic Proposition Set
     */
    public Set<AtomicProposition> getAtomicPropositionSet() {
        return this.labeling.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    /**
     * Get an atomic proposition set of a given state by the labeling function.
     *
     * @param state state
     * @return atomic proposition set where all elements hold in the given state
     */
    public Set<AtomicProposition> getAtomicPropositionSetOfState(State state) {
        return this.labeling.get(state);
    }

}
