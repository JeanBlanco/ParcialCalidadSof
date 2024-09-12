package edu.unac;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleRandomProviderTest {
    @Test
    void testNextIntWithZeroBound() {
        SimpleRandomProvider randomProvider = new SimpleRandomProvider();
        assertThrows(IllegalArgumentException.class, () -> randomProvider.nextInt(0));
    }

    @Test
    void testNextIntWithNegativeBound() {
        SimpleRandomProvider randomProvider = new SimpleRandomProvider();
        assertThrows(IllegalArgumentException.class, () -> randomProvider.nextInt(-1));
    }

    @Test
    void testNextIntEdgeBound() {
        SimpleRandomProvider randomProvider = new SimpleRandomProvider();
        assertEquals(0, randomProvider.nextInt(1)); // Should always return 0 when bound is 1
    }

    @Test
    void testNextInt() {
        int max = 5;
        SimpleRandomProvider randomProvider = new SimpleRandomProvider();
        int result = randomProvider.nextInt(max);
        assertTrue(result >= 0 && result <= max);
    }
}