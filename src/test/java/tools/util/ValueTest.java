package tools.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ValueTest {

    private static final String INITIAL_VALUE = "init";
    private static final String UPDATED_VALUE = "updated";

    private Value<String> value;

    @Before
    public void setUp() throws Exception {
        value = new Value<String>(INITIAL_VALUE);
    }

    @Test
    public void testUpdate() {
        assertEquals(INITIAL_VALUE, value.getCurrentValue());
        value.setCurrentValue(UPDATED_VALUE);
        assertEquals(UPDATED_VALUE, value.getCurrentValue());
        value.update();
        assertEquals(UPDATED_VALUE, value.getCurrentValue());
        value.revert();
        assertEquals(UPDATED_VALUE, value.getCurrentValue());
    }

    @Test
    public void testRevert() {
        value.setCurrentValue(UPDATED_VALUE);
        assertEquals(UPDATED_VALUE, value.getCurrentValue());
        value.revert();
        assertEquals(INITIAL_VALUE, value.getCurrentValue());
    }

    @Test
    public void testGetCurrentValue() {
        assertEquals(INITIAL_VALUE, value.getCurrentValue());
    }

    @Test
    public void testSetCurrentValue() {
        value.setCurrentValue(UPDATED_VALUE);
        assertEquals(UPDATED_VALUE, value.getCurrentValue());
    }

    @Test
    public void testIsModified() {
        assertFalse(value.isModified());
        value.setCurrentValue(UPDATED_VALUE);
        assertTrue(value.isModified());
        value.update();
        assertFalse(value.isModified());
    }

}
