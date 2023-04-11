package TestOfTaskAndManeger;

import Manager.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Task.*;

import javax.naming.TimeLimitExceededException;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskOrFileTest <T extends TaskManager> {

    Epic epicWithoutSubtask;
    TaskManager taskManager;
    Task task;
    Task task1;
    Epic epicInProgress;
    Epic epicToNew;
    Epic epicToDone;
    Subtask subtaskInProgress;
    Subtask subtaskDone;
    Subtask subtaskNew;

    @BeforeEach
    public void setUp() throws IOException, TimeLimitExceededException {
        taskManager = Managers.getDefault();
    }

    @AfterEach
    public void tearDown() {
        taskManager.removeAllTask();
        taskManager = Managers.getDefault();
    }

    @Test
    void shouldCrateOneTask() {

        final Task savedTask = task;
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = new ArrayList<>();
        tasks.add(task);

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }


    @Test
    void shouldRemoveTaskById() {
        Task taskForRemove = new Task("HELLO" , StatusTask.NEW , "WHATS WRONG" ,
                LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                Duration.ofMinutes(100));

        Assertions.assertNotNull(taskForRemove);
    }

    @Test
    void shouldRemoveAllTask() throws IOException, TimeLimitExceededException {
        taskManager.crateTask(task);
        taskManager.crateTask(task1);

        taskManager.removeAllTask();

        Assertions.assertEquals(taskManager.printAllTask().size() , 0);
        Assertions.assertEquals(taskManager.printAllEpic().size() , 0);
    }

    @Test
    void shouldCreateEpicWithoutSubtask() throws IOException {
        taskManager.createEpic(epicToDone);
        final Task savedTask = epicToDone;

        assertEquals(epicToDone, savedTask, "Задачи не совпадают.");

        final List<Epic> tasks = new ArrayList<>();
        tasks.add(epicToDone);

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(epicToDone, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    public void shouldMakeStatusForEpic() {
        Assertions.assertEquals(StatusTask.NEW  , epicWithoutSubtask.getStatusTask());
    }

    @Test
    public void shouldChangeStatusEpicToDONE() {
        Assertions.assertEquals(StatusTask.DONE, epicToDone.getStatusTask());
    }


    @Test
    public void shouldChangeStatusEpicToINPROGRESS() {
        Assertions.assertEquals(StatusTask.IN_PROGRESS, epicInProgress.getStatusTask());
    }

    @Test
    public void shouldChangeStatusEpicToNEW() {
        Assertions.assertEquals(StatusTask.NEW, epicToNew.getStatusTask());
    }

    @Test
    void shouldRemoveEpicByIdWithSubtask() throws IOException {
       taskManager = new InMemoryTaskManager();
       taskManager.removeAllTask();
      Epic epicToNu  = new Epic("It should removed" , "WHat is NEXT?" ,
              LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
              Duration.ofMinutes(100));
        taskManager.createEpic(epicToNu);
       Subtask subtaskNu = new Subtask(epicToNu.getId() , "SUBTASK" , StatusTask.NEW , "SUBTASK" ,LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
               Duration.ofMinutes(100));
        taskManager.createSubTask(subtaskNu);
        taskManager.removeEpicById(epicToNu.getId());
        int size = taskManager.printAllEpic().size();
        int sizeSub = taskManager.printAllSubtask().size();

        assertEquals(0 , size);
        assertEquals(0 , sizeSub);
    }

    @Test
    void shouldPrintAllTask() {
        List<Task> allTasks = new ArrayList<>();

        allTasks.add(task);
        allTasks.add(task1);
        Assertions.assertEquals(allTasks , taskManager.printAllTask());
    }

    @Test
    void shouldPrintAllEpic() {

        List<Task> allEpics = new ArrayList<>();
        allEpics.add(epicWithoutSubtask);
        allEpics.add(epicToDone);
        allEpics.add(epicToNew);
        allEpics.add(epicInProgress);

        Assertions.assertEquals(allEpics.size() , taskManager.printAllEpic().size());

    }

    @Test
    void shouldCreateSubTask() throws IOException {
        Epic epicToNEw = new Epic("NEW", "sd" ,
                LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                Duration.ofMinutes(100));
        taskManager.createEpic(epicToNEw);

        Subtask subtask = new Subtask(epicToNEw.getId(), "Test", StatusTask.NEW, "NOPE" ,
                LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                Duration.ofMinutes(100));
        taskManager.createSubTask(subtask);

        Assertions.assertNotNull(subtask , "У подзадачи нет эпика");
        assertEquals(subtask.getId(), taskManager.printAllSubtaskByEpic(epicToNEw).get(0).getId());

    }

    @Test
    void shouldNotCreateSubTask() throws IOException {
        Epic epicToNEw = new Epic("@_@", ":(" ,
                LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                Duration.ofMinutes(100));
        taskManager.createEpic(epicToNEw);
        int invalidId = -1;
        Subtask subtaskInvalid = new Subtask(invalidId, "Test", StatusTask.NEW, "NOPE" ,
                LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                Duration.ofMinutes(100));
        assertThrows(NullPointerException.class, () -> {
            taskManager.createSubTask(subtaskInvalid);
        });
    }
    @Test
    void shouldPrintTaskById() {
        Assertions.assertEquals(task , taskManager.printTaskById(task.getId()));
    }

    @Test
    void shouldPrintEpicById() {
        assertNotNull(epicInProgress, "No epic in this Area");
        final Integer epicToPrint = epicInProgress.getId();
        Assertions.assertEquals(taskManager.printEpicById(epicToPrint) , epicInProgress);
    }

    @Test
    void shouldPrintSubtaskById() {
        List<Subtask> allOfThem = new ArrayList<>();
        allOfThem.add(subtaskDone);
        allOfThem.add(subtaskInProgress);
        allOfThem.add(subtaskNew);

        assertEquals(allOfThem.size() , taskManager.printAllSubtask().size());
    }

    @Test
    void removeSubTaskById() {
        int size = taskManager.printAllSubtask().size();
        taskManager.removeSubTaskById(subtaskInProgress.getId());
        size = size - 1;
        assertEquals(size , taskManager.printAllSubtask().size());
    }

    @Test
    void shouldPrintAllSubtaskByEpic() {
        assertEquals(subtaskInProgress, taskManager.printAllSubtaskByEpic(epicInProgress).get(0));
        assertEquals(subtaskDone , taskManager.printAllSubtaskByEpic(epicToDone).get(0));
        assertEquals(subtaskNew , taskManager.printAllSubtaskByEpic(epicToNew).get(0));
    }

    @Test
    void shouldNotPrintAllSubtaskByInvalidEpic() throws IOException {
        Epic epic = new Epic("NEW", "sd", LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                Duration.ofMinutes(100));
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask(epic.getId(), "Test", StatusTask.NEW, "NOPE" ,LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                Duration.ofMinutes(100));
        taskManager.createSubTask(subtask);

        Epic invalidEpic = new Epic("OLD", "sd" ,
                LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                Duration.ofMinutes(100));
        invalidEpic.setId(epic.getId() + 1);

        List<Subtask> subtasks = taskManager.printAllSubtaskByEpic(invalidEpic);
        assertTrue(subtasks.isEmpty());
    }

    @Test
    void shouldPintAllSubtask() {

        List<Task> AllOfSubtask = new ArrayList<>();
        AllOfSubtask.add(subtaskNew);
        AllOfSubtask.add(subtaskDone);
        AllOfSubtask.add(subtaskInProgress);

        Assertions.assertEquals(AllOfSubtask.get(0) , subtaskNew);
        Assertions.assertEquals(AllOfSubtask.get(1) , subtaskDone);
        Assertions.assertEquals(AllOfSubtask.get(2) , subtaskInProgress);
        Assertions.assertEquals(AllOfSubtask.size() , 3);
    }
    @Test
    public void shouldSortTaskByTime() {
        assertEquals(5 , taskManager.getPrioritizedTasks().size());
        List<Task> sort = new ArrayList<>(taskManager.getPrioritizedTasks());
        List<Task> notSort = new ArrayList<>();
        notSort.add(subtaskInProgress);
        notSort.add(subtaskNew);
        notSort.add(epicWithoutSubtask);
        notSort.add(subtaskDone);
        notSort.add(epicInProgress);

        assertEquals(sort , notSort);
    }
}