package com.timelogger.timeloggerapi;

import java.time.LocalDateTime;

public class WorkerEntry {
    public WorkerEntryStatus workerEntryStatus;
    public LocalDateTime workerEntryDateTime;

    public WorkerEntry(WorkerEntryStatus workerEntryStatus) {
        this.workerEntryStatus = workerEntryStatus;
        this.workerEntryDateTime = LocalDateTime.now();
    }
}
