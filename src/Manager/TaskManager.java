package Manager;

import Task.*;

import java.io.IOException;
import java.util.List;

public interface TaskManager {

    void removeAllTask();

    void crateTask(Task task) throws IOException;

    void updateTask(Task task);


    void createEpic(Epic epic) throws IOException;

    void updateEpic(Epic epic);

    void updateEpicStatus(int id);

    List<Task> printAllTask();

    List<Subtask> printAllSubtask();

    List<Epic> printAllEpic();

    void createSubTask(Subtask subtask) throws IOException;

    void updateSubtaskTitle(Subtask subtask);

    Task printTaskById(int id);

    Epic printEpicById(int id);

    Subtask printSubtaskById(int id);

    void removeTaskById(int id);

    void removeEpicById(int id);

    void removeSubTaskById(int id);

    List<Task> getHistory() throws IOException;

    List<Subtask> printAllSubtaskByEpic(Epic epic);
}
