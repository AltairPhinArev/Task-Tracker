package TestOfTaskAndManeger;

import Manager.*;
import Task.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

/*
    @author Andrey Panasiuk
    Git @AltairPhinArev
 */

class FileBackedTasksManagerTest  {
    File file;
    Epic epicWithoutSubtask;
    FileBackedTasksManager  fileBackedTasksManager;
    Task task;
    Task task1;
    Epic epicInProgress;
    Epic epicToNew;
    Epic epicToDone;
    Subtask subtaskInPogress;
    Subtask subtaskDone;
    Subtask subtaskNew;


    @BeforeEach
    public void setUp() throws IOException {
        file = new File("Resources.csv");
        fileBackedTasksManager = new FileBackedTasksManager(file);
        epicWithoutSubtask = new Epic("Believe"  , "StatusTask.NEW" ,
                LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                Duration.ofMinutes(100));
        fileBackedTasksManager.createEpic(epicWithoutSubtask);

        task = new Task("TEST",StatusTask.DONE,"task" ,
                LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                Duration.ofMinutes(100));
        fileBackedTasksManager.crateTask(task);

        task1 = new Task("TASK" , StatusTask.DONE , "vtask" ,
                LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                Duration.ofMinutes(100));
        fileBackedTasksManager.crateTask(task1);

        epicToNew = new Epic("Understand How it work",  "epic" ,
                LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                Duration.ofMinutes(100));
        fileBackedTasksManager.createEpic(epicToNew);

        epicToDone = new Epic("Hard learn" ,  "epic1" ,
                LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                Duration.ofMinutes(100));
        fileBackedTasksManager.createEpic(epicToDone);

        epicInProgress = new Epic( "Hard learn" , "epic1" ,
                LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                Duration.ofMinutes(100));
        fileBackedTasksManager.createEpic(epicInProgress);

        subtaskDone = new Subtask(epicToDone.getId()  , "TEST" , StatusTask.DONE , "TEST" ,
                LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                Duration.ofMinutes(100));
        fileBackedTasksManager.createSubTask(subtaskDone);

        subtaskInPogress = new Subtask(epicInProgress.getId()  , "TEST" , StatusTask.IN_PROGRESS , "TEST" ,
                LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                Duration.ofMinutes(100));
        fileBackedTasksManager.createSubTask(subtaskInPogress);

        subtaskNew = new Subtask(epicToNew.getId()  , "TEST" , StatusTask.NEW , "TEST" ,
                LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                Duration.ofMinutes(100));
        fileBackedTasksManager.createSubTask(subtaskNew);
    }

    @Test
    public void shouldSaveInFile() throws  NullPointerException {

        ArrayList<String> lines = new ArrayList<>();
        try {
            Reader fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            String line = br.readLine();

            line = br.readLine();
            lines.add(line);
            br.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Assertions.assertEquals(1, lines.size());
    }

    @Test
    public void shouldLoadFromFileOneTask() throws IOException {
        FileBackedTasksManager.loadFromFile(file);
        ArrayList<Task> taskfrom = new ArrayList<>();
        taskfrom.add(fileBackedTasksManager.printAllTask().get(0));
        Assertions.assertEquals(1, taskfrom.size());
    }

    @Test
    public void shouldNotloadfromFile() throws IOException {
            Assertions.assertThrows(NullPointerException.class , () -> {
                fileBackedTasksManager.printTaskById(280);
        });
    }
}
