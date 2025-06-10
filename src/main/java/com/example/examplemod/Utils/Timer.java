package com.example.examplemod.Utils;

public class Timer {
    private long lastMS = 0L;

    public void reset() {
        lastMS = System.currentTimeMillis();
    }

    public long getPassedTimeMs() {
        return System.currentTimeMillis() - lastMS;
    }

    public boolean hasPassed(long ms) {
        return getPassedTimeMs() >= ms;
    }
}