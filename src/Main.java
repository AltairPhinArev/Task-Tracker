import java.util.Scanner;
import Task.*;
import Manager.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager tm = new TaskManager();
        Epic epic = new Epic(1,  "Understand How it work" , "EEE" , "AWS SERVER");
            tm.createEpic(epic);
            tm.printAllEpic();
    }
}
