package Manager;
import Task.*;
import java.util.List;

public interface TaskManager {

    void removeAllTask();

     void crateTask(Task task);
     void updateTask(Task task);
     void removeTask(int id);
     void createEpic(Epic epic);
     void updateEpic(Epic epic);
     void updEpicStatus(int id);

     List<Task> printAllTask();

     List<Subtask> printAllSubtask();
     List<Epic> printAllEpic();

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
     List<Task> getHistory();
     List<Subtask> printAllSubtaskByEpic(Epic epic);
}
