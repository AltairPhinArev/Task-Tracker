import java.util.Scanner;
import Task.*;
import Manager.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
            TaskManager tm = new TaskManager();
            Task t1 = new Task();
            Task t2 = new Task();
            Epic epic = new Epic();
            Subtask subtask = new Subtask();
            Epic epic1 = new Epic();
            Subtask subtask1 = new Subtask();
            Epic epic2 = new Epic();
            Subtask subtask2 = new Subtask();


            tm.setIdNewNum(tm.getIdNewNum());
            t1.setTitle(t1.getTitle());
            tm.crateTask(t1);
            System.out.println(t1);
            tm.changeStatusTask(t1);
            System.out.println(t1);


            tm.setIdNewNum(tm.getIdNewNum());
            t2.setStatusTask(t2.getStatusTask());
            tm.crateTask(t2);
            tm.changeStatusTask(t2);
            tm.changeStatusTask(t2);
            System.out.println(t2);
            tm.setIdNewNum(tm.getIdNewNum());
            epic1.setTitle("Купить машину");
            subtask1.setEpicId(tm.getIdNewNum());
            //tm.updateEpicStatus();
            System.out.println(epic1);
            tm.removeAllTask(t1);
            tm.removeAllTask(epic1);
            tm.removeAllTask(subtask1);



    }
}
