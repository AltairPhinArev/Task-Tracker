package Manager;

import Task.Task;
import java.util.List;

/*
    @author Andrey Panasiuk
    Git @AltairPhinArev
 */

public interface HistoryManager {
    void addHistory(Task task);

    void remove(int id);

    List<Task> getHistory();
}
