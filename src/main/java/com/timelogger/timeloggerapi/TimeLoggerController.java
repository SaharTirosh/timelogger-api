package com.timelogger.timeloggerapi;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "api/v1/timelogger/")
public class TimeLoggerController {
    TimeLoggerService loggerService = new TimeLoggerService();

    @PostMapping(path = "enter")
    public void handleEnterRequest(@RequestParam(required = true) int id) throws Exception {
        System.out.println("Starting to handle enter request for user id: " + id);
        try{
            loggerService.addNewEntry(id, WorkerEntryStatus.ENTER);
            System.out.println("Done handling enter request for user id: " + id + ".");
        } catch (Exception e) {
            System.out.println("Exception occurred while handling enter request for user id: " + id + ". error: " + e.getMessage());
            throw e;
        }
    }

    @PostMapping(path = "exit")
    public void handleExitRequest(@RequestParam(required = true) int id) throws Exception {
        System.out.println("Starting to handle exit request for user id: " + id);
        try {
            loggerService.addNewEntry(id, WorkerEntryStatus.EXIT);
            System.out.println("Done handling exit request for user id: " + id + ".");
        } catch (Exception e) {
            System.out.println("Exception occurred while handling exit request for user id: " + id + ". error: " + e.getMessage());
            throw e;
        }
    }

    @GetMapping(path = "info")
    public String getWorkerInfo(@RequestParam(required = false) Integer id) throws Exception {
        System.out.println("Starting to handle get worker info request");
        try {
            return id != null ? loggerService.getWorkerInfoById(id) : loggerService.getAllWorkersInfo();
        } catch (Exception e) {
            System.out.println("Exception occurred while handling get worker info request. error: " + e.getMessage());
            throw e;
        }
    }
}

