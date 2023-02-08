import Task.*;
import Manager.*;

public class Main {

    public static void main(String[] args) {
        TaskManager tm = new TaskManager();
        Epic epic = new Epic( "Understand How it work" , "NEW" , "PRACTICUM");
        Task task = new Task("Hard learn java" , "IN-PROGRESS" , "PRACTICUM");
        Task task1 = new Task( "Start to run" , "NEW" , "NOTHING ELSE DECRIPTION");
        Subtask subtask = new Subtask( 3, "Hard learn java" , "DONE" , "PRACTICUM");
        Subtask subtask1 = new Subtask( 3, "Hard learn java" , "DONE" , "PRACTICUM");

            tm.crateTask(task);
            tm.crateTask(task1);
            tm.createEpic(epic);
            tm.createSubTask(subtask);
            tm.createSubTask(subtask1);
            System.out.println(tm.printAllEpic());
            System.out.println(tm.printAllSubtask());
            System.out.println(tm.printAllTask());
            tm.removeAllTask();
            System.out.println(tm.printAllTask());
    }
}
