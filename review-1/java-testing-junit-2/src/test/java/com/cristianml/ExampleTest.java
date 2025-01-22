package com.cristianml;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ExampleTest {

    @Test
    public void sumarTest() {

        Example example = new Example();

        int result = example.sumar(8, 5);

        assertEquals(13, result);
        assertTrue(12 < result);
        assertFalse(12 > result);
        assertNotNull(result);
        assertInstanceOf(Integer.class, result);
    }

}
