package edu.unac;

import edu.unac.exception.ConsecutiveCharacterException;
import edu.unac.exception.DuplicateSaltException;
import edu.unac.exception.InvalidLengthException;
import edu.unac.exception.RepeatedCharacterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SaltGeneratorTest {

    @Mock
    private SimpleRandomProvider randomProvider;

    @InjectMocks
    private SaltGenerator saltGenerator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateSalt_validSalt() throws Exception {

        when(randomProvider.nextInt(anyInt())).thenReturn(0, 5, 10, 20, 25); // A, F, K, U, Z

        String result = saltGenerator.generateSalt(5);

        assertNotNull(result);
        assertEquals(5, result.length());
        verify(randomProvider, times(5)).nextInt(anyInt());
    }


    @Test
    void testGenerateSalt_invalidLength() {

        assertThrows(InvalidLengthException.class, () -> saltGenerator.generateSalt(2));
    }

    @Test
    void testGenerateSalt_repeatedCharacterException() throws Exception {

        when(randomProvider.nextInt(anyInt())).thenReturn(0, 0, 0); // Will create a salt with repeated characters

        assertThrows(RepeatedCharacterException.class, () -> saltGenerator.generateSalt(3));
    }

    @Test
    void testGenerateSalt_consecutiveCharacterException() throws Exception {

        when(randomProvider.nextInt(anyInt())).thenReturn(0, 1, 2); // Will create a salt with consecutive characters


        assertThrows(ConsecutiveCharacterException.class, () -> saltGenerator.generateSalt(3));
    }

    @Test
    void testGenerateSalt_duplicateSaltException() throws Exception {

        when(randomProvider.nextInt(anyInt())).thenReturn(0, 5, 10); // A, F, K
        saltGenerator.generateSalt(3); // Generar y almacenar una sal válida

        when(randomProvider.nextInt(anyInt())).thenReturn(0, 5, 10);
        assertThrows(DuplicateSaltException.class, () -> saltGenerator.generateSalt(3));
    }

}

