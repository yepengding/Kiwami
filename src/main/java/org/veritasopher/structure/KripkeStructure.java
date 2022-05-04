package org.veritasopher.structure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.veritasopher.element.AtomicProposition;
import org.veritasopher.element.State;
import org.veritasopher.element.Transition;
import org.veritasopher.exception.Assert;
import org.veritasopher.exception.SystemException;
import org.veritasopher.logic.LTLFormula;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Finite Kripke Structure
 *
 * @author Yepeng Ding
 */
@RequiredArgsConstructor
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

    private final Set<LTLFormula> ltlProperties;

    /**
     * Whether the structure semantics has been checked
     */
    private boolean isChecked;

    public KripkeStructure() {
        this.states = new HashSet<>();
        this.initStates = new HashSet<>();
        this.transitions = new HashSet<>();
        this.labeling = new HashMap<>();
        this.ltlProperties = new HashSet<>();
        this.isChecked = false;
    }


    /**
     * Calculate the maximal step.
     * If the structure terminates, then the maximal step (step boundary) exists.
     *
     * @return maximal step if exists. Otherwise, null.
     */
    public Optional<Integer> calculateMaximalStep() {
        // Check legacy
        if (!this.isChecked) {
            this.checkLegacy();
        }

        int maxStep = 0;

        for (State i :
                this.initStates) {
            Set<State> visitedStates = new HashSet<>();
            Stack<State> trace = new Stack<>();
            visitedStates.add(i);
            trace.push(i);

            Optional<Integer> currentMaxStep = findMaxStepByDFS(visitedStates, trace, trace.size());

            if (currentMaxStep.isPresent()) {
                // Update the global maximal step.
                maxStep = Math.max(currentMaxStep.get(), maxStep);
            } else {
                // If there exists an infinite path from this initial state, then the maximal step does not exist.
                return Optional.empty();
            }

        }

        return Optional.of(maxStep);
    }

    /**
     * Check whether the structure terminates (has a terminal state).
     * Check whether there exists the maximal step.
     * Check whether there is no cycle in the transition set.
     *
     * @return true if terminates
     */
    public boolean isTermination() {
        // Check legacy
        if (!this.isChecked) {
            this.checkLegacy();
        }

        Optional<Integer> maxStep = this.calculateMaximalStep();

        // If the maximal step exists, then the structure terminates.
        return maxStep.isPresent();
    }

    /**
     * Find the maximal step by DFS.
     *
     * @param visited        visited state set
     * @param trace          trace recording the current visiting path
     * @param currentMaxStep the current maximal step
     * @return an updated maximal step value of null if an infinite path (a cycle) exists
     */
    private Optional<Integer> findMaxStepByDFS(Set<State> visited, Stack<State> trace, int currentMaxStep) {
        Optional<Integer> maxStep = Optional.of(currentMaxStep);

        // Find adjacent states of the current state.
        Set<State> adjacent = this.transitions.stream()
                .filter(t -> t.src().equals(trace.peek()))
                .map(Transition::dst)
                .collect(Collectors.toSet());

        for (State s :
                adjacent) {
            if (trace.contains(s)) {
                // If a cycle is detected (i.e., the current state is on the trace), then the maximal step does not exist.
                maxStep = Optional.empty();
            } else if (!visited.contains(s)) {
                visited.add(s);
                trace.push(s);
                maxStep = findMaxStepByDFS(visited, trace, Math.max(trace.size(), currentMaxStep));
            }
        }

        // Remove the current state from the trace since traces derived from this state have been searched.
        trace.pop();

        return maxStep;
    }


    /**
     * Check the legacy.
     *
     * @return true if well-structured
     */
    public boolean checkLegacy() {
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

        this.isChecked = true;
        return true;
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
