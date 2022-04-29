package org.veritasopher.unit;

import org.junit.Test;
import org.veritasopher.element.State;
import org.veritasopher.encoder.NaiveEncoder;
import org.veritasopher.structure.KripkeStructure;
import org.veritasopher.utils.Sample;

import static org.junit.Assert.assertTrue;

/**
 * Encoder Test
 */
public class EncoderTest {

    @Test
    public void testMutualExclusion() {
        KripkeStructure mutex = Sample.getMutualExclusionStructure();
        mutex.checkLegacy();

        NaiveEncoder naiveEncoder = new NaiveEncoder(mutex);

        String smtProgram = naiveEncoder.generateSMT();
        System.out.println(smtProgram);
    }

    @Test
    public void testNaiveEncoder() {
        KripkeStructure k0 = Sample.getKripkeStructure0();
        k0.checkLegacy();

        NaiveEncoder naiveEncoder = new NaiveEncoder(k0);
        naiveEncoder.printStateMap();

        State s3 = k0.getStateByDefinition("s3").orElseThrow();
        NaiveEncoder.EncodedState eS3 = naiveEncoder.getEncodedState(s3).orElseThrow();
        assertTrue(eS3.code()[2]);
    }

}
