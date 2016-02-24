package com.baxamoosa;

import java.util.Random;

public class Utility {
    public static int getRandBetween(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(max - min) + min;
    }
}
