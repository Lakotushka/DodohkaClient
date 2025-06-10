package com.example.examplemod.Utils;

public class Timer1 {
    private long lastMs;

    public Timer1() {
        this.lastMs = -1;
    }
    public boolean passedMs(long ms) {
        return getTime() - lastMs >= ms;
    }
    public void reset() {
        lastMs = getTime();
    }
    private long getTime() {
        return System.currentTimeMillis();
    }
}