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

        SystemException exception = assertThrows(SystemException.class, () -> new NaiveEncoder(structure));
        assertEquals("The initial state set is empty.", exception.getMessage());
    }

    @Test
    public void testFaultyStructure1() {
        KripkeStructure structure = Sample.getFaultyStructure1();

        SystemException exception = assertThrows(SystemException.class, () -> new NaiveEncoder(structure));
        assertEquals("Initial state (s0) is not defined in the state set.", exception.getMessage());
    }

}
