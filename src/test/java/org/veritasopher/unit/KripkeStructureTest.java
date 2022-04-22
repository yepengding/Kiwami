package org.veritasopher.unit;

import org.junit.Test;
import org.veritasopher.element.AtomicProposition;
import org.veritasopher.element.State;
import org.veritasopher.structure.EncodedKripkeStructure;
import org.veritasopher.structure.KripkeStructure;
import org.veritasopher.utils.Input;

import java.util.Set;

public class KripkeStructureTest {

    @Test
    public void testEncodedState() {
        KripkeStructure k0 = Input.getKripkeStructure0();

        EncodedKripkeStructure eK0 = new EncodedKripkeStructure(k0);
        State s3 = k0.getStateByDefinition("s3").orElseThrow();
        boolean[] eS3 = eK0.getEncodedState(s3).orElseThrow();
        assert eS3[2];
    }

    @Test
    public void testAtomicPropositionSet() {
        Set<AtomicProposition> atomicPropositions = Input.getKripkeStructure0().getAtomicPropositionSet();
        assert atomicPropositions.size() == 5;
    }

}
