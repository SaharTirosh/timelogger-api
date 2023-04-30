package com.timelogger.timeloggerapi;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Worker {
    public ArrayList<WorkerShiftTime> shifts;
    public Lock lock;

    public Worker() {
        shifts = new ArrayList<>();
        lock = new ReentrantLock();
    }
}