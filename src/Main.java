import Task.*;
import Manager.*;

public class Main {

    public static void main(String[] args) {
        TaskManager tm = Managers.getDefault();
        Epic epic = new Epic("Understand How it work" , StatusTask.NEW , "PRACTICUM");
        Epic epic1 = new Epic("Hard learn" , StatusTask.NEW , "PRACR");
        Task task = new Task("Hard learn java" , StatusTask.IN_PROGRESS , "PRACTICUM");
        Task task1 = new Task( "Start to run" , StatusTask.NEW, "NOTHING ELSE DECRIPTION");
        Subtask subtask = new Subtask( 3, "Hard learn java" , StatusTask.NEW , "PRACTICUM");
        Subtask subtask1 = new Subtask( 3, "Hard learn java" , StatusTask.NEW , "PRACTICUM");
        Subtask subtask2 = new Subtask(3 , "PRINT Subtask" , StatusTask.DONE , "PRACTICUM");
        Subtask subtask3 = new Subtask(4 , "THough about life" , StatusTask.NEW , "I am empty");

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

            System.out.println(tm.printTaskById(2));
            System.out.println(tm.printEpicById(4));
            tm.removeTask(2);

            System.out.println();
            System.out.println("Histyory by ID");
            System.out.println();
            System.out.println(tm.getHistory());

    }
}
