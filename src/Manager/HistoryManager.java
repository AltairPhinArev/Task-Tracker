package Manager;

import Task.Task;

import java.util.ArrayList;
import java.util.List;

public interface HistoryManager {

    ArrayList<Task> getHistory();
    void addHistory(Task task);
    void add(Task task);

    void updateHistory(int id);

}