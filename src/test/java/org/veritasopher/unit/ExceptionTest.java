package org.veritasopher.unit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.veritasopher.encoder.NaiveEncoder;
import org.veritasopher.exception.SystemException;
import org.veritasopher.structure.KripkeStructure;
import org.veritasopher.utils.Sample;

/**
 * Exception Test
 */
public class ExceptionTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testFaultyStructure0() {
        exceptionRule.expect(SystemException.class);
        exceptionRule.expectMessage("The initial state set is empty.");

        KripkeStructure structure = Sample.getFaultyStructure0();

        NaiveEncoder naiveEncoder = new NaiveEncoder(structure);
        naiveEncoder.encode();
    }

    @Test
    public void testFaultyStructure1() {
        exceptionRule.expect(SystemException.class);
        exceptionRule.expectMessage("Initial state (s0) is not defined in the state set.");

        KripkeStructure structure = Sample.getFaultyStructure1();
        NaiveEncoder naiveEncoder = new NaiveEncoder(structure);
        naiveEncoder.encode();
    }

}
