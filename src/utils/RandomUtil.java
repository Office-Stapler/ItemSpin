package utils;

import java.util.Random;

public class RandomUtil {
    private final static Random random = new Random();

    // lower inclusive, upper exclusive
    public static int getInt(int lower, int upper) {
        return random.nextInt(upper - lower) + lower;
    }

    public static double getDouble() {
        return random.nextDouble();
    }


}
