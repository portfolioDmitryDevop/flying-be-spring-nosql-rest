package com.cybernite.flying.utils;

import com.cybernite.flying.common.Constants;

import java.util.concurrent.ThreadLocalRandom;
public class FlyingUtils {

    public static long generateId(){
        return ThreadLocalRandom.current().nextLong(Constants.MIN_ID,Constants.MAX_ID);
    }
}
