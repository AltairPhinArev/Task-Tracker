package Manager;

import Task.*;
import java.util.ArrayList;

public class InMemoryHistoryManager extends Managers implements HistoryManager {

    private final ArrayList<Task> taskId;

    public InMemoryHistoryManager() {
        this.taskId = new ArrayList<>();
    }

    @Override
    public ArrayList<Task> getHistory() {
        return taskId;
    }

    @Override
    public void addHistory(Task task) {
        if(taskId.size() <= 10) {
            taskId.add(task);
        } else {
            taskId.remove(0);
        }
    }

    @Override
    public void updateHistory(int id) {
        taskId.remove(id);
    }
}
