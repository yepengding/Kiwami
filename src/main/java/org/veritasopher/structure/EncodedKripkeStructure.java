package org.veritasopher.structure;

import org.veritasopher.element.AtomicProposition;
import org.veritasopher.element.State;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EncodedKripkeStructure {

    // Map<atomic proposition, index>
    private final Map<AtomicProposition, Integer> apMap;

    // Map<state, encoded array>
    private final Map<State, boolean[]> stateMap;

    private final KripkeStructure kripkeStructure;

    public EncodedKripkeStructure(KripkeStructure kripkeStructure) {
        this.apMap = new HashMap<>();
        this.stateMap = new HashMap<>();
        this.kripkeStructure = kripkeStructure;
        initAtomicPropositionMap();
        initStateMap();
    }

    /**
     * Init atomic proposition with unique indices
     */
    private void initAtomicPropositionMap() {
        int index = 0;
        for (AtomicProposition ap :
                this.kripkeStructure.getAtomicPropositionSet()) {
            this.apMap.put(ap, index++);
        }
    }

    private void initStateMap() {
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
     * Get an encoded state by a given state
     *
     * @param state state
     * @return either an encoded state or null
     */
    public Optional<boolean[]> getEncodedState(State state) {
        return Optional.ofNullable(this.stateMap.get(state));
    }

    public void printStateMap() {
        this.stateMap.forEach(((state, booleans) -> {
            System.out.println(state.toString());
            System.out.println(Arrays.toString(booleans));
            System.out.println();
        }));
    }

}
