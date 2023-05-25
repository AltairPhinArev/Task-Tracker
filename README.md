# java-kanban

Project Description
-
Task-Trecker is a REST service for creating, managing various types
of tasks, as well as storing their history. The manager is able to serialize and
deserialize its state into a CSV file and an HTTP server.


Technologies used:
-

- Java 11, GSON, Http Server, JUnit Test Framework

Application functionality:
-
The application provides the opportunity to:

-  History storage is implemented based on its LinkedList implementation.
-  REST API architecture is implemented via Java HttpServer.
- When serialized to a file and to an HTTP server, the manager's state (tasks, history, counters) is completely transferred.
- There is an output of priority tasks.
- The execution (time and status) of Epic tasks depends only on their internal subtasks.
![image](https://github.com/AltairPhinArev/Task-Tracker/assets/113471380/f76408fc-0a57-4bf2-897b-1f6aadba07a6)
