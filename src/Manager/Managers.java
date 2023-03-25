package Manager;


public abstract class Managers {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
    public static FileBackedTasksManager getDefaultFile() {
        return new FileBackedTasksManager();
    }
}

