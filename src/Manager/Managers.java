package Manager;

import Server.HttpTaskManager;

import java.io.IOException;

public abstract class Managers {

    public static TaskManager getDefaultMemory() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getDefault(String url, int port, String key) throws IOException {
        return new HttpTaskManager(url);
    }
}

