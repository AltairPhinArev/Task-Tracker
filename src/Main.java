import java.util.Scanner;
import Task.*;
import Manager.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager tm = new TaskManager();
            Epic epic = new Epic(2 , "Understand How it work" , "NEW" , "AWS SERVER");
            epic.setTitle("Understand How it work");
            epic.setStatusTask("NEW");
            tm.addEpic(epic);
            System.out.println(epic);
            tm.printAllEpic();

    }
}
