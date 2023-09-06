package com.automation.core.utilities.number;

import java.util.concurrent.ThreadLocalRandom;

public class IntUtils {


    public static int random(final int max){
        return random(1, max);
    }


    public static int random(final int min, final int max){
        return max == 0 ? 0 : ThreadLocalRandom.current().nextInt(min, max + 1);
    }

}
