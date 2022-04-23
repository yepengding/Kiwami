package org.veritasopher.unit;

import org.junit.Test;
import org.veritasopher.element.State;
import org.veritasopher.encoder.NaiveEncoder;
import org.veritasopher.structure.EncodedKripkeStructure;
import org.veritasopher.structure.KripkeStructure;
import org.veritasopher.utils.Input;

public class EncoderTest {

    @Test
    public void testNaiveEncoder() {
        KripkeStructure k0 = Input.getKripkeStructure0();

        NaiveEncoder naiveEncoder = new NaiveEncoder(k0);
        EncodedKripkeStructure eK0 = naiveEncoder.encode();
        eK0.printStateMap();

        State s3 = k0.getStateByDefinition("s3").orElseThrow();
        boolean[] eS3 = eK0.getEncodedState(s3).orElseThrow();
        assert eS3[2];
    }

}
