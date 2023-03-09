package Manager;

import Task.*;

import java.util.List;

public interface TaskManager {

    void removeAllTask();

    void crateTask(Task task);

    void updateTask(Task task);


    void createEpic(Epic epic);

    void updateEpic(Epic epic);

    void updateEpicStatus(int id);

    List<Task> printAllTask();

    List<Subtask> printAllSubtask();

    List<Epic> printAllEpic();

    void createSubTask(Subtask subtask);

    void updateSubtaskTitle(Subtask subtask);

    Task printTaskById(int id);

    Epic printEpicById(int id);

    Subtask printSubtaskById(int id);

    void removeTaskById(int id);

    void removeEpicById(int id);

    void removeSubTaskById(int id);

    List<Task> getHistory();

    List<Subtask> printAllSubtaskByEpic(Epic epic);
}
