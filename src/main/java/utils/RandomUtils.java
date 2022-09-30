package utils;

import java.util.Random;

public class RandomUtils {

    private static Random rand = new Random();

    public static Integer getRandomIntInRange(int range) {
        return rand.nextInt(range);
    }
}
