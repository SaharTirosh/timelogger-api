package com.timelogger.timeloggerapi;

import java.time.LocalDateTime;

public class WorkerShiftTime {
    public LocalDateTime workerEntryTime = null;
    public LocalDateTime workerExitTime = null;

    public WorkerShiftTime() {
        this.workerEntryTime = LocalDateTime.now();
    }

    public LocalDateTime getWorkerEntryTime() {
        return workerEntryTime;
    }

    public void setWorkerEntryTime(LocalDateTime workerEntryTime) {
        this.workerEntryTime = workerEntryTime;
    }

    public LocalDateTime getWorkerExitTime() {
        return workerExitTime;
    }

    public void setWorkerExitTime(LocalDateTime workerExitTime) {
        this.workerExitTime = workerExitTime;
    }
}
