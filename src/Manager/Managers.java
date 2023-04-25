package Manager;

import Server.HttpTaskManager;

public abstract class Managers {

    public static TaskManager getDefaultMemory() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getDefault(String url) {
        return new HttpTaskManager(url);
    }
}