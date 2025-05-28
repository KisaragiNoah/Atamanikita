package com.kisaraginoah.atamanikita.util;

public class MathUtils {

    public static long customPow(long val, long cal) {
        if (cal == 0) {
            return 0;
        }
        return (long) Math.pow(val, cal);
    }
}
