import Manager.*;
import Task.*;
import Task.Task;

import javax.naming.TimeLimitExceededException;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;


public class Main {
    public static void main(String[] args) throws IOException, TimeLimitExceededException {
       // FileBackedTasksManager fn = new FileBackedTasksManager(new File("Resources.csv"));
      //FileBackedTasksManager taskManager = new FileBackedTasksManager(new File("Resources.csv"));



                // FileBackedTasksManager fn = new FileBackedTasksManager(new File("Resources.csv"));

                FileBackedTasksManager taskManager = new FileBackedTasksManager(new File("Resources.csv"));


                Task task = new Task("TEST",StatusTask.DONE,"task" ,
                        LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                        Duration.ofMinutes(100));
                taskManager.crateTask(task);



                Task task1 = new Task("TASK" , StatusTask.DONE , "vtask",
                        LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                        Duration.ofMinutes(100));
                taskManager.crateTask(task1);

                Epic epicToNew = new Epic("Understand How it work",  "epic",
                        LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                        Duration.ofMinutes(100));
                taskManager.createEpic(epicToNew);



                Epic epicToDone = new Epic("Hard learn" ,  "epic1" ,
                        LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                        Duration.ofMinutes(100));
                taskManager.createEpic(epicToDone);



                Epic epicInProgress = new Epic( "Hard learn" , "epic1" ,
                        LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                        Duration.ofMinutes(100));
                taskManager.createEpic(epicInProgress);



                Subtask subtaskDone = new Subtask(epicToDone.getId()  , "TEST" , StatusTask.DONE , "TEST" ,
                        LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                        Duration.ofMinutes(100));
                taskManager.createSubTask(subtaskDone);



                Subtask subtaskInProgress = new Subtask(epicInProgress.getId()  , "TEST" , StatusTask.IN_PROGRESS , "TEST",
                        LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                        Duration.ofMinutes(100));
                taskManager.createSubTask(subtaskInProgress);

                Subtask subtaskNew = new Subtask(epicToNew.getId()  , "TEST" , StatusTask.NEW , "TEST",
                        LocalDateTime.of(20100, 10 , 18 , 20 , 18) ,
                        Duration.ofMinutes(100));
                taskManager.createSubTask(subtaskNew);

                System.out.println(taskManager.printAllEpic());
                System.out.println("Sorted -->");
                System.out.println(taskManager.getPrioritizedTasks());
            }
}