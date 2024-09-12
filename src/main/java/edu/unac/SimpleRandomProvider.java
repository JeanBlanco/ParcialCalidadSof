package edu.unac;

import java.util.Random;

public class SimpleRandomProvider {
    private final Random random = new Random();

    public SimpleRandomProvider(){};

    public int nextInt(int bound) {
        return random.nextInt(bound);
    }
}
