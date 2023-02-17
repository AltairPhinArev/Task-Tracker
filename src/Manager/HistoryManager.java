package Manager;

import Task.Task;
import java.util.List;

public interface HistoryManager {

    List<Task> getHistory();

    void addHistory(Task task);

    void updateHistory(int id);

}