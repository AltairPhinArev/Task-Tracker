package Manager;

import Task.*;
import java.util.List;
import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {

    private final ArrayList<Task> taskId;

    public InMemoryHistoryManager() {
        this.taskId = new ArrayList<>();
    }

    public void addTask(Task task) {
        this.taskId.add(task);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return taskId;
    }

    @Override
    public void addHistory(Task task) {
        taskId.add(task);
    }
    @Override
    public void add(Task task) {
        addHistory(task);
    }
    @Override
    public void updateHistory(int id) {
        taskId.remove(id);
    }
}
