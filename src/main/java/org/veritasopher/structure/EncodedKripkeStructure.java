package org.veritasopher.structure;

import org.veritasopher.element.AtomicProposition;
import org.veritasopher.element.State;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

/**
 * Encoded Kripke Structure
 *
 * @param apMap    Map: atomic proposition -> index
 * @param stateMap Map: state -> encoded array
 * @author Yepeng Ding
 */
public record EncodedKripkeStructure(
        Map<AtomicProposition, Integer> apMap,
        Map<State, boolean[]> stateMap) {


    /**
     * Get an encoded state by a given state
     *
     * @param state state
     * @return either an encoded state or null
     */
    public Optional<boolean[]> getEncodedState(State state) {
        return Optional.ofNullable(this.stateMap.get(state));
    }

    /**
     * Print state map
     */
    public void printStateMap() {
        this.stateMap.forEach(((state, booleans) -> {
            System.out.println(state.toString());
            System.out.println(Arrays.toString(booleans));
            System.out.println();
        }));
    }

}
