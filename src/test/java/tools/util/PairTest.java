package tools.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class PairTest {

    @Test
    public void testPair() {

        long cle = 40000l;
        String value = "value";

        Pair<Long, String> paire = new Pair<Long, String>(cle, value);

        assertNotNull(paire);
        assertEquals(cle, paire.getKey().longValue());
        assertEquals(value, paire.getValue());
    }
}
