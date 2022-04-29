package org.veritasopher.encoder;

import org.veritasopher.element.AtomicProposition;
import org.veritasopher.element.State;
import org.veritasopher.exception.Assert;
import org.veritasopher.exception.SystemException;
import org.veritasopher.structure.KripkeStructure;

import java.util.*;

/**
 * Naive Encoder
 *
 * @author Yepeng Ding
 */
public class NaiveEncoder {

    private final KripkeStructure kripkeStructure;

    /**
     * Atomic proposition encoding map.
     * Map: atomic proposition -> atomic proposition index
     */
    private final Map<AtomicProposition, Integer> apMap;

    /**
     * State encoding map.
     * Map: state -> encoded state
     */
    private final Map<State, EncodedState> stateMap;

    public NaiveEncoder(KripkeStructure kripkeStructure) {
        this.apMap = new HashMap<>();
        this.stateMap = new HashMap<>();
        this.kripkeStructure = kripkeStructure;

        checkLegacy();
        encodeAtomicProposition();
        encodeState();
    }

    /**
     * Encode the kripke structure in the constructor
     */
    public String encodeAsString() {

        return null;
    }

    /**
     * Encode atomic proposition: atomic proposition -> index
     */
    private void encodeAtomicProposition() {
        int index = 0;
        for (AtomicProposition ap :
                this.kripkeStructure.getAtomicPropositionSet()) {
            this.apMap.put(ap, index++);
        }
    }

    /**
     * Encode state map: state -> encoded state (boolean array)
     */
    private void encodeState() {
        int index = 0;
        for (State state :
                this.kripkeStructure.getStates()) {
            boolean[] code = new boolean[this.apMap.size()];
            this.kripkeStructure.getAtomicPropositionSetOfState(state).stream()
                    .map(this.apMap::get)
                    .forEach(i -> code[i] = true);
            this.stateMap.put(state, new EncodedState(index, code));
        }
    }

    /**
     * Check the legacy of a Kripke structure.
     */
    private void checkLegacy() {
        // Initial state set is not empty
        Assert.isTrue(this.kripkeStructure.getInitStates().size() > 0,
                new SystemException("The initial state set is empty."));

        // State set is not empty
        Set<State> stateSet = this.kripkeStructure.getStates();
        Assert.isTrue(stateSet.size() > 0,
                new SystemException("The state set is empty."));

        // Initial states are in the state set
        this.kripkeStructure.getInitStates()
                .forEach(s ->
                        Assert.isTrue(stateSet.contains(s),
                                new SystemException("Initial state (%s) is not defined in the state set.".formatted(s.definition())))
                );

        // Transition states are in the state set
        this.kripkeStructure.getTransitions()
                .forEach(t -> {
                    Assert.isTrue(stateSet.contains(t.src()),
                            new SystemException("State (%s) is not defined in the state set.".formatted(t.src().definition())));
                    Assert.isTrue(stateSet.contains(t.dst()),
                            new SystemException("State (%s) is not defined in the state set.".formatted(t.dst().definition())));
                });
    }

    /**
     * Get an encoded state by a given state
     *
     * @param state state
     * @return either an encoded state or null
     */
    public Optional<EncodedState> getEncodedState(State state) {
        return Optional.ofNullable(this.stateMap.get(state));
    }

    /**
     * Print state map.
     */
    public void printStateMap() {
        this.stateMap.forEach(((state, encodedState) -> {
            System.out.println(state.toString());
            System.out.println(Arrays.toString(encodedState.code));
            System.out.println();
        }));
    }

    /**
     * Encoded State
     *
     * @param index state index
     * @param code  state code
     */
    public record EncodedState(
            int index,
            boolean[] code
    ) {
    }

}
