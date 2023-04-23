package TestOfTaskAndManeger;

import Manager.HistoryManager;
import Manager.Managers;
import Manager.TaskManager;
import Task.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.TimeLimitExceededException;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryHistoryManagerTest {
    TaskManager taskManager;
    HistoryManager historyManager;
    Task task;
    Task task1;
    @BeforeEach
    void setUp() throws IOException, TimeLimitExceededException {
        historyManager = Managers.getDefaultHistory();
        task = new Task(  "ForTest" , StatusTask.DONE ,"null" ,
                LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                Duration.ofMinutes(100));
        task1 = new Task( "ForTest" , StatusTask.NEW ,"null" ,
                LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                Duration.ofMinutes(100));
        taskManager = Managers.getDefaultMemory();

        taskManager.crateTask(task);
        taskManager.crateTask(task1);
    }

    @Test
    void shouldGetHistory() {
        historyManager.addHistory(task);
        historyManager.addHistory(task);
        historyManager.addHistory(task);
        historyManager.addHistory(task1);

        List<Task> testTaskHistory = new ArrayList<>();
        testTaskHistory.add(task);
        testTaskHistory.add(task1);

        assertEquals(historyManager.getHistory().size() , 2);
        assertEquals(historyManager.getHistory().size() , 2);
        assertEquals(historyManager.getHistory().size() , testTaskHistory.size());
        assertEquals(historyManager.getHistory() , testTaskHistory);
    }

    @Test
    void shouldAddHistory() {
        historyManager.addHistory(task);
        historyManager.addHistory(task);
        historyManager.addHistory(task);
        historyManager.addHistory(task1);
        historyManager.addHistory(task);

        assertEquals(2 ,historyManager.getHistory().size());
    }

    @Test
    void shouldRemove() {
        historyManager.addHistory(task);
        historyManager.addHistory(task);
        historyManager.addHistory(task1);
        historyManager.remove(task.getId());

        assertEquals(1 , historyManager.getHistory().size());
    }

    @Test
    public void shouldNotAddNullTask() {
        assertThrows(NullPointerException.class, () -> {
            historyManager.addHistory(null);
        });
    }


}