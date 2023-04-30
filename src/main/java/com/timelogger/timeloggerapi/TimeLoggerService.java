package com.timelogger.timeloggerapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Map;

@Service

public class TimeLoggerService {
    TimeLoggerDictionary timeLoggerDictionary = new TimeLoggerDictionary();

    public void addNewEntry(int id, WorkerEntryStatus workerEntryStatus) throws Exception {
        WorkerEntry workerEntry = new WorkerEntry(workerEntryStatus);
        timeLoggerDictionary.addNewEntry(id, workerEntry);
    }

    public String getWorkerInfoById(int id) throws JsonProcessingException {
        return getWorkerShiftTimeJson(id, timeLoggerDictionary.getWorkerShifts(id));
    }

    public String getAllWorkersInfo() throws JsonProcessingException {
        return getAllWorkersShiftTimeJson();
    }

    public String getWorkerShiftTimeJson(int id, ArrayList<WorkerShiftTime> shifts) throws JsonProcessingException {
        WorkerJsonObject workerJsonObject = new WorkerJsonObject(id);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        workerJsonObject.SetDates(shifts);
        String js = objectMapper.writeValueAsString(workerJsonObject);
        return js;
    }

    public String getAllWorkersShiftTimeJson() throws JsonProcessingException {
        ArrayList<String> allWorkersShiftsJson = new ArrayList<String>();
        Map<Integer, Worker> allWorkersShifts = timeLoggerDictionary.getWorkersShifts();
        for (int id : allWorkersShifts.keySet()) {
            ArrayList<WorkerShiftTime> WorkerShifts = timeLoggerDictionary.getWorkerShifts(id);
            allWorkersShiftsJson.add(getWorkerShiftTimeJson(id, WorkerShifts));
        }
        return allWorkersShiftsJson.toString();
    }
}
