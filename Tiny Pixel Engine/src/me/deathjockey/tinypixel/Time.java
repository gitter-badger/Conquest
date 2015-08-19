package me.deathjockey.tinypixel;

public class Time {

    public static double delta;

    public static double getTime(){
        return (double) System.nanoTime();
    }

    public static double getDelta() {
        return delta;
    }

    public static void setDelta(double delta) {
        Time.delta = delta;
    }
}
