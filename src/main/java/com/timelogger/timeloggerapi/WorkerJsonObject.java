package com.timelogger.timeloggerapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class WorkerJsonObject {

    private int EmployeeId;
    private ArrayList<ArrayList<String>> Dates;

    public WorkerJsonObject(int employeeId) {
        Dates = new ArrayList<>();
        EmployeeId = employeeId;
    }

    @JsonProperty("employee_id")
    public int getEmployeeId() {
        return EmployeeId;
    }

    @JsonProperty("dates")
    public ArrayList<ArrayList<String>> getDates() {
        return Dates;
    }

    public void SetDates(ArrayList<WorkerShiftTime> shiftTimes) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy-HH:mm");
        Dates = new ArrayList<>();

        for (WorkerShiftTime shift : shiftTimes) {
            ArrayList<String> shiftDates = new ArrayList<>();
            shiftDates.add(shift.getWorkerEntryTime().format(formatter));

            if (shift.getWorkerExitTime() != null) {
                shiftDates.add(shift.getWorkerExitTime().format(formatter));
            }

            Dates.add(shiftDates);
        }
    }
}
