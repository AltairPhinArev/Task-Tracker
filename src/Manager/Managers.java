package Manager;

public class Managers {

    public static InMemoryTaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static TaskManager getTaskManager() {
        return new InMemoryTaskManager();
    }

}

