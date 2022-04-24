package org.veritasopher.unit;

import org.junit.Test;
import org.veritasopher.encoder.NaiveEncoder;
import org.veritasopher.exception.SystemException;
import org.veritasopher.structure.KripkeStructure;
import org.veritasopher.utils.Sample;

/**
 * Exception Test
 */
public class ExceptionTest {

    @Test(expected = SystemException.class)
    public void testFaultyStructure() {
        KripkeStructure structure = Sample.getFaultyStructure();

        NaiveEncoder naiveEncoder = new NaiveEncoder(structure);
        naiveEncoder.encode();
    }

}
