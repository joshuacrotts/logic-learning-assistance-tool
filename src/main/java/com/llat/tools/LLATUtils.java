package com.llat.tools;

import java.util.concurrent.ThreadLocalRandom;

public class LLATUtils {

    /**
     * Returns a random integer between min and max.
     *
     * @param min
     * @param max
     * @return random integer
     */
    public static int randomInt(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException(" Max must be smaller than min.");
        }

        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    /**
     * Returns a random integer between 0 and max.
     *
     * @param max
     * @return random integer
     */
    public static int randomInt(int max) {
        return randomInt(0, max);
    }
}
