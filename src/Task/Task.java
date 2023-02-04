package Task;

public class Task {
    private int id;
    private String title;
    private String statusTask;

    public String getStatusTask() {
        return statusTask;
    }

    public void setStatusTask(String statusTask) {
        this.statusTask = statusTask;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id = " + id +
                ", title = '" + title + '\'' +
                ", statusTask = '" + statusTask + '\'' +
                '}';
    }


}
