package com.timelogger.timeloggerapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TimeLoggerDictionary {
    private Map<Integer, Worker> timeLoggerDictionary;
    private ReentrantReadWriteLock lock;
    private Lock readLock;
    private Lock writeLock;

    public TimeLoggerDictionary() {
        this.lock = new ReentrantReadWriteLock();
        this.readLock = this.lock.readLock();
        this.writeLock = this.lock.writeLock();
        this.timeLoggerDictionary = new HashMap<>();
    }

    // This function uses read and write locks to ensure thread-safety when
    // accessing and modifying a worker's shifts.
    // The function first checks if the worker exists, and if so, it acquires a
    // write lock and creates a copy of the worker's shifts.
    // Then the function acquires a write lock and adds the new entry to the
    // worker's shifts.
    public void addNewEntry(int id, WorkerEntry workerEntry) throws Exception {
        createWorkerIfNotExists(id);
        Worker workerObj = timeLoggerDictionary.get(id);
        workerObj.lock.lock();
        try {
            ArrayList<WorkerShiftTime> workerShifts = workerObj.shifts;
            int lastShiftIndex = workerShifts.size() - 1;
            WorkerShiftTime lastShiftValue = lastShiftIndex == -1 ? null : workerShifts.get(lastShiftIndex);

            if (workerEntry.workerEntryStatus == WorkerEntryStatus.EXIT) {
                addExitEntry(workerEntry, lastShiftIndex, lastShiftValue, workerObj);
            }

            if (workerEntry.workerEntryStatus == WorkerEntryStatus.ENTER) {
                addEnterEntry(lastShiftIndex, lastShiftValue, workerObj);
            }
        } finally {
            workerObj.lock.unlock();
        }
    }

    // This function uses read and write locks to ensure thread-safety when
    // accessing and modifying a worker's shifts.
    // The function first checks if the worker exists, and if so, it acquires a read
    // lock and creates a copy of the worker's shifts.
    public ArrayList<WorkerShiftTime> getWorkerShifts(int id) {
        if (!timeLoggerDictionary.containsKey(id)) {
            throw new IllegalArgumentException("worker not found");
        }
        Worker workerObj = timeLoggerDictionary.get(id);
        workerObj.lock.lock();
        ArrayList<WorkerShiftTime> workerShiftCopy = (ArrayList<WorkerShiftTime>) workerObj.shifts.clone();
        workerObj.lock.unlock();
        return workerShiftCopy;
    }

    public Map<Integer, Worker> getWorkersShifts() {
        return this.timeLoggerDictionary;
    }

    private void createNewWorker(int id) {
        timeLoggerDictionary.put(id, new Worker());
    }

    private void addEnterEntry(int lastShiftIndex, WorkerShiftTime lastShiftValue, Worker workerObj) throws Exception {
        ArrayList<WorkerShiftTime> workerShifts = workerObj.shifts;
        if (lastShiftIndex == -1) {
            workerShifts.add(new WorkerShiftTime());
            return;
        }
        if (lastShiftValue.workerExitTime == null) {
            throw new Exception("You can't enter without exiting");
        } else {
            workerShifts.add(new WorkerShiftTime());
        }
    }

    private void addExitEntry(WorkerEntry workerEntry, int lastShiftIndex, WorkerShiftTime lastShiftValue,
            Worker workerObj) throws Exception {
        ArrayList<WorkerShiftTime> workerShifts = workerObj.shifts;
        if (lastShiftIndex == -1 || lastShiftValue.workerExitTime != null) {
            throw new Exception("You can't exit without entering");
        } else {
            workerShifts.get(lastShiftIndex).workerExitTime = workerEntry.workerEntryDateTime;
        }
    }

    // This is a thread-safe function that prevents the creation of multiple workers
    // with the same ID by using read and write locks.
    // If the worker does not exist, the function releases the read lock and
    // acquires a write lock before checking again and creating a new worker if
    // necessary.
    // This ensures that only one thread can create a worker with a specific ID at a
    // time.
    private void createWorkerIfNotExists(int id) {
        readLock.lock();
        if (!timeLoggerDictionary.containsKey(id)) {
            readLock.unlock();
            var writeLock = this.lock.writeLock();
            writeLock.lock();
            if (!timeLoggerDictionary.containsKey(id)) {
                createNewWorker(id);
            }
            writeLock.unlock();
        } else {
            readLock.unlock();
        }
    }
}
