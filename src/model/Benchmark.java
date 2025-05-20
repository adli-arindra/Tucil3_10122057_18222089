package model;

public class Benchmark {
    private int visitedNodes = 0;
    private long startTime = 0;
    private long elapsedTime = 0;

    public synchronized int getVisitedNodes() {
        return this.visitedNodes;
    }

    public synchronized void setVisitedNodes(int nodes) {
        this.visitedNodes = nodes;
    }

    public synchronized void reset() {
        this.visitedNodes = 0;
        this.startTime = 0;
        this.elapsedTime = 0;
    }

    public synchronized void startTimer() {
        this.startTime = System.nanoTime();

    }

    public synchronized void stopTimer() {
        if (this.startTime != 0) {
            this.elapsedTime = System.nanoTime() - this.startTime;
        }
    }

    public synchronized long getElapsedTimeMillis() {
        return this.elapsedTime / 1_000_000;
    }

    public synchronized long getElapsedTimeMicros() {
        return this.elapsedTime / 1_000;
    }

    public synchronized long getElapsedTimeNanos() {
        return this.elapsedTime;
    }
}
