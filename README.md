# Work Time Logging Server

This is a Java 17 and Maven-based server that logs workers entrance and exit times.

## API Reference


## Server URL 

**https://timelogger-service.herokuapp.com**

The server supports the following endpoints for interacting with employees:

**Logging entrance time for an employee**
```
POST https://timelogger-service.herokuapp.com/enter?id=***
```
Logs the entrance time for the employee with the specified ID. Returns a `200 OK` response.

**Logging exit time for an employee**
```
POST https://timelogger-service.herokuapp.com/exit?id=***
```
Logs the exit time for the employee with the specified ID. Returns a `200 OK` response.

**Retrieving all enter and exit times for an employee**
```
GET https://timelogger-service.herokuapp.com/info?id=***
```
Retrieves all enter and exit times for the employee with the specified ID in JSON format.

Example response:
```
{
    "employee_id": 1,
    "dates": [
        ["01-01-2023-09:00", "01-01-2023-10:00"],
        ["01-02-2023-10:30", "01-02-2023-21:00"],
        ["02-02-2023-08:01", "02-02-2023-18:46"],
        ["03-03-2023-09:00"] # Only enter time was received
    ]
}
```

**Retrieving all enter and exit times for all employees**
```
GET https://timelogger-service.herokuapp.com/info
```
Retrieves all enter and exit times for all employees in the system in JSON format.

Example response:
```
[
    {
        "employee_id": 1,
        "dates": [
            ["01-01-2023-09:00", "01-01-2023-10:00"],
            ["01-02-2023-10:30", "01-02-2023-21:00"],
            ["02-02-2023-08:01", "02-02-2023-18:46"],
            ["03-03-2023-09:00"] # Only enter time was received
        ]
    },
    {
        "employee_id": 2,
        "dates": [
            ["01-05-2023-09:00", "01-05-2023-10:00"],
            ["02-05-2023-10:30", "02-05-2023-21:00"]
        ]
    }
]
```

### Server Definitions

- If 2 consecutive enter/exit requests arrive, the second request returns an error. (HTTP code: 500)
- If an exit request arrives without an earlier enter request, the request returns an error. (HTTP code: 500)
- If an enter request arrives for a non-existing worker ID, a new ID is created.
- All responses are in JSON format.
- No database is required. All data is saved in memory while the server is running.

## Running The Server Locally

1. Clone the project: `git clone https://github.com/SaharTirosh/timelogger-api.git`
2. Navigate to the project directory: `cd timelogger-api\src\main\java\com\timelogger\timeloggerapi\TimeLoggerApiApplication.java`
3. Run the function `main()`
4. Access the server through `localhost:8080`

## Testing The Server 
1. Navigate to the project directory: `cd NSO-assignment\timelogger-api\src\test\java\com\timelogger\timeloggerapi\TimeLoggerApiApplicationTests.java`
2. Run each of the next functions:
    - testGoodCases() - Checks all the operations of the server.
    - testJustExit() -  Checks a case when exit is called without any entrance before.
    - testMultipleEnter() - Checks a case of multiple entrances without any exit.
