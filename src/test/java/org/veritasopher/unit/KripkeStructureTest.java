package org.veritasopher.unit;

import org.junit.Test;
import org.veritasopher.element.State;
import org.veritasopher.structure.KripkeStructure;
import org.veritasopher.utils.Sample;

import java.util.Set;

/**
 * Kripke Structure Test
 */
public class KripkeStructureTest {

    @Test
    public void testMutualExclusion() {
        KripkeStructure structure = Sample.getMutualExclusionStructure();
        State s0 = structure.getStateByDefinition("s0").orElseThrow();
        State s1 = structure.getStateByDefinition("s1").orElseThrow();
        State s3 = structure.getStateByDefinition("s3").orElseThrow();
        assert structure.getAtomicPropositionSetOfState(s0).size() == 0;
        assert structure.getAtomicPropositionSetOfState(s1).size() == 1;
        assert structure.getAtomicPropositionSetOfState(s3).size() == 2;
    }

    @Test
    public void testStructure0() {
        KripkeStructure structure = Sample.getKripkeStructure0();
        Set<State> states = structure.getStates();
        assert states.size() == 5;
    }

}
