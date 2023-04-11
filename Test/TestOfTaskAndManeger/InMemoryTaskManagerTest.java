package TestOfTaskAndManeger;

import Manager.*;
import Task.*;
import org.junit.jupiter.api.BeforeEach;

import javax.naming.TimeLimitExceededException;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

class InMemoryTaskManagerTest extends TaskOrFileTest <InMemoryTaskManager> {
    @BeforeEach
    public void setUp() throws IOException, TimeLimitExceededException {
        taskManager = new InMemoryTaskManager();

        epicWithoutSubtask = new Epic("Believe" ,StatusTask.NEW , "StatusTask.NEW" ,
                LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                Duration.ofMinutes(100));
        taskManager.createEpic(epicWithoutSubtask);

        task = new Task("TEST",StatusTask.DONE,"task" ,
                LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                Duration.ofMinutes(100));
        taskManager.crateTask(task);

        task1 = new Task("TASK" , StatusTask.DONE , "vtask",
                LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                Duration.ofMinutes(100));
        taskManager.crateTask(task1);

        epicToNew = new Epic("Understand How it work",  "epic",
                LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                Duration.ofMinutes(100));
        taskManager.createEpic(epicToNew);

        epicToDone = new Epic("Hard learn" ,  "epic1" ,
                LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                Duration.ofMinutes(100));
        taskManager.createEpic(epicToDone);

        epicInProgress = new Epic( "Hard learn" , "epic1" ,
                LocalDateTime.of(2020, 10 , 18 , 20 , 18) ,
                Duration.ofMinutes(100));
        taskManager.createEpic(epicInProgress);

        subtaskDone = new Subtask(epicToDone.getId()  , "TEST" , StatusTask.DONE , "TEST" ,
                LocalDateTime.of(2001, 10 , 18 , 20 , 18) ,
                Duration.ofMinutes(100));
        taskManager.createSubTask(subtaskDone);

        subtaskInProgress = new Subtask(epicInProgress.getId()  , "TEST" , StatusTask.IN_PROGRESS , "TEST",
                LocalDateTime.of(1900, 10 , 18 , 20 , 18) ,
                Duration.ofMinutes(100));
        taskManager.createSubTask(subtaskInProgress);

        subtaskNew = new Subtask(epicToNew.getId()  , "TEST" , StatusTask.NEW , "TEST",
                LocalDateTime.of(1971, 10 , 18 , 20 , 18) ,
                Duration.ofMinutes(100));
        taskManager.createSubTask(subtaskNew);
    }

}