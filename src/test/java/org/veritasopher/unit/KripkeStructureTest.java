package org.veritasopher.unit;

import org.junit.Test;
import org.veritasopher.element.AtomicProposition;
import org.veritasopher.utils.Input;

import java.util.Set;

public class KripkeStructureTest {

    @Test
    public void testAtomicPropositionSet() {
        Set<AtomicProposition> atomicPropositions = Input.getKripkeStructure0().getAtomicPropositionSet();
        assert atomicPropositions.size() == 5;
    }

}
