import Task.*;
import Manager.*;

public class Main {

    public static void main(String[] args) {
        TaskManager tm = new TaskManager();
        Epic epic = new Epic( "Understand How it work" , "NEW" , "PRACTICUM");
        Epic epic1 = new Epic("Hard learn " , "NEW" , "PRACR");
        Task task = new Task("Hard learn java" , "IN-PROGRESS" , "PRACTICUM");
        Task task1 = new Task( "Start to run" , "NEW" , "NOTHING ELSE DECRIPTION");
        Subtask subtask = new Subtask( 3, "Hard learn java" , "DONE" , "PRACTICUM");
        Subtask subtask1 = new Subtask( 3, "Hard learn java" , "NEW" , "PRACTICUM");
        Subtask subtask2 = new Subtask(3 , "PRINT Subtask" , "IN-PROGRESS" , "PRACTICUM");
        Subtask subtask3 = new Subtask(4 , "THough about life" , "DONE" , "I am empty");

            tm.crateTask(task);
            tm.crateTask(task1);
            tm.createEpic(epic);
            tm.createEpic(epic1);
            tm.createSubTask(subtask);
            tm.createSubTask(subtask1);
            tm.createSubTask(subtask2);
            tm.createSubTask(subtask3);
            System.out.println(tm.printAllEpic());
            System.out.println(tm.printAllSubtask());
            System.out.println(tm.printAllTask());
            tm.removeAllTask();
            tm.removeSubTaskById(6);
            tm.removeSubTaskById(8);
            System.out.println(tm.printEpicById(4));

    }
}
