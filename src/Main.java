import Manager.*;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {


            FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();


            FileBackedTasksManager.loadFromFile();

            System.out.println(fileBackedTasksManager.printAllEpic());
            System.out.println(fileBackedTasksManager.printAllTask());
            System.out.println(fileBackedTasksManager.printAllSubtask());
            System.out.println();
            System.out.println(fileBackedTasksManager.printTaskById(1));

            System.out.println("HISTORY YOUR TASKS");

           fileBackedTasksManager.historyToString(Managers.getDefaultHistory());
            //System.out.println(tm.getHistory());

    }
}