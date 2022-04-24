package org.veritasopher.encoder;

import org.veritasopher.element.AtomicProposition;
import org.veritasopher.element.State;
import org.veritasopher.exception.Assert;
import org.veritasopher.exception.SystemException;
import org.veritasopher.structure.EncodedKripkeStructure;
import org.veritasopher.structure.KripkeStructure;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Naive Encoder
 *
 * @author Yepeng Ding
 */
public class NaiveEncoder {

    private final KripkeStructure kripkeStructure;

    /**
     * Atomic proposition encoding map.
     * Map: atomic proposition -> index
     */
    private final Map<AtomicProposition, Integer> apMap;

    /**
     * State encoding map.
     * Map: state -> encoded state (boolean array)
     */
    private final Map<State, boolean[]> stateMap;

    public NaiveEncoder(KripkeStructure kripkeStructure) {
        this.apMap = new HashMap<>();
        this.stateMap = new HashMap<>();
        this.kripkeStructure = kripkeStructure;
    }

    /**
     * Encode the kripke structure in the constructor
     */
    public EncodedKripkeStructure encode() {
        checkLegacy();
        encodeAtomicProposition();
        encodeState();
        return new EncodedKripkeStructure(this.apMap, this.stateMap);
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
        for (State state :
                this.kripkeStructure.getStates()) {
            boolean[] encodedState = new boolean[this.apMap.size()];
            this.kripkeStructure.getAtomicPropositionSetOfState(state).stream()
                    .map(this.apMap::get)
                    .forEach(i -> encodedState[i] = true);
            this.stateMap.put(state, encodedState);
        }
    }

    /**
     * Check the legacy of a Kripke structure
     */
    private void checkLegacy() {
        // Initial state set is not empty
        Assert.isTrue(kripkeStructure.getInitStates().size() > 0,
                new SystemException("The initial state set is empty."));

        // State set is not empty
        Set<State> stateSet = kripkeStructure.getStates();
        Assert.isTrue(stateSet.size() > 0,
                new SystemException("The state set is empty."));

        // Initial states are in the state set
        kripkeStructure.getInitStates()
                .forEach(s ->
                        Assert.isTrue(stateSet.contains(s),
                                new SystemException("Initial state (%s) is not defined in the state set.".formatted(s.definition())))
                );

        // Transition states are in the state set
        kripkeStructure.getTransitions()
                .forEach(t -> {
                    Assert.isTrue(stateSet.contains(t.src()),
                            new SystemException("State (%s) is not defined in the state set.".formatted(t.src().definition())));
                    Assert.isTrue(stateSet.contains(t.dst()),
                            new SystemException("State (%s) is not defined in the state set.".formatted(t.dst().definition())));
                });
    }

}
