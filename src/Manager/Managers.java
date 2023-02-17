package Manager;


public class Managers {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getHist() {
        return new InMemoryHistoryManager();
    }
}
