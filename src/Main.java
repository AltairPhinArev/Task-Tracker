import Task.*;
import Manager.*;

public class Main {

    public static void main(String[] args) {
        TaskManager tm = Managers.getDefault();
        Epic epic = new Epic("Understand How it work" , StatusTask.NEW,  "epic"); //1
        Epic epic1 = new Epic("Hard learn" , StatusTask.NEW , "epic1"); //2
        Task task = new Task("Hard learn java" , StatusTask.IN_PROGRESS , "task");
        Task task1 = new Task( "Start to run" , StatusTask.NEW, "task1");
        Subtask subtask = new Subtask( 3, "Hard learn java" , StatusTask.NEW , "subtask");
        Subtask subtask1 = new Subtask( 3, "Hard learn java" , StatusTask.NEW , "subtask2");
        Subtask subtask2 = new Subtask(3 , "PRINT Subtask" , StatusTask.IN_PROGRESS , "PRACTICUM");
        Subtask subtask3 = new Subtask(4 , "THough about life" , StatusTask.NEW , "subtask3");
        Subtask subtask4 = new Subtask(4,"Hello" , StatusTask.NEW , "subtask4");
        Task task2 = new Task("Hello" , StatusTask.NEW , "task2");

            tm.crateTask(task);  // id-1
            tm.crateTask(task1); // id-2

            tm.createEpic(epic);  // id-3
            tm.createEpic(epic1); // id-4

            tm.createSubTask(subtask); // id-5
            tm.createSubTask(subtask1); // id-6
            tm.createSubTask(subtask2); // id-7
            tm.createSubTask(subtask3); // id-8
            tm.createSubTask(subtask4); // id-9

            tm.crateTask(task2); // id-10

            System.out.println(tm.printEpicById(3));
            System.out.println(tm.printTaskById(10));
            System.out.println(tm.printTaskById(2));
            System.out.println(tm.printEpicById(4));
            System.out.println(tm.printSubtaskById(8));


            System.out.println();
            System.out.println("Histyory by ID");
            System.out.println();
            System.out.println(tm.getHistory());
    }
}