package Manager;
import Task.*;
import java.util.ArrayList;

public interface TaskManager {

    void removeAllTask();

     void crateTask(Task task);
     void updateTask(Task task);
     void removeTask(int id);
     void createEpic(Epic epic);
     void updateEpic(Epic epic);
     void updEpicStatus(int id);

     ArrayList<Task> printAllTask();

     ArrayList<Subtask> printAllSubtask();
     ArrayList<Epic> printAllEpic();

     void createSubTask(Subtask subtask);
     void updateSubtataskTitle(Subtask subtask);
    // Получение задачи по его id
     Task printTaskById(int id);
     Epic printEpicById(int id);
     Subtask printSubtaskById(int id );
    //Удаление по Id
     void removeTaskById(int id);
     void removeEpicById(int id);
     void removeSubTaskById(int id);
     ArrayList<Subtask> printAllSubtaskByEpic(Epic epic);
}
