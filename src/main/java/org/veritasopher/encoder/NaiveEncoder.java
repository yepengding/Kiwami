package org.veritasopher.encoder;

import org.veritasopher.element.AtomicProposition;
import org.veritasopher.element.State;
import org.veritasopher.exception.SystemException;
import org.veritasopher.structure.EncodedKripkeStructure;
import org.veritasopher.structure.KripkeStructure;

import java.util.HashMap;
import java.util.Map;

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
        if (this.kripkeStructure.getInitStates().size() == 0) {
            throw new SystemException("Kripke structure does not have initial states.");
        }
    }

}
