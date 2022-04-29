package org.veritasopher.unit;

import org.junit.Test;
import org.veritasopher.encoder.NaiveEncoder;
import org.veritasopher.exception.SystemException;
import org.veritasopher.structure.KripkeStructure;
import org.veritasopher.utils.Sample;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Exception Test
 */
public class ExceptionTest {

    @Test
    public void testFaultyStructure0() {
        KripkeStructure structure = Sample.getFaultyStructure0();

        NaiveEncoder naiveEncoder = new NaiveEncoder();
        SystemException exception = assertThrows(SystemException.class, () -> naiveEncoder.encode(structure));
        assertEquals("The initial state set is empty.", exception.getMessage());
    }

    @Test
    public void testFaultyStructure1() {
        KripkeStructure structure = Sample.getFaultyStructure1();
        NaiveEncoder naiveEncoder = new NaiveEncoder();

        SystemException exception = assertThrows(SystemException.class, () -> naiveEncoder.encode(structure));
        assertEquals("Initial state (s0) is not defined in the state set.", exception.getMessage());
    }

}
