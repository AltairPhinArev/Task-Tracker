import Manager.*;
import Task.StatusTask;
import Task.Task;

import java.io.File;
import java.io.IOException;

import static Manager.FileBackedTasksManager.historyToString;

public class Main {

    public static void main(String[] args) throws IOException {

            File file = new File("Resources.csv");

            FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
            FileBackedTasksManager.loadFromFile(file);

            System.out.println(fileBackedTasksManager.printAllEpic());
            System.out.println(fileBackedTasksManager.printAllTask());
            System.out.println(fileBackedTasksManager.printAllSubtask());
            System.out.println();
            System.out.println(fileBackedTasksManager.printTaskById(1));

            System.out.println("HISTORY YOUR TASKS");

    }
}