package Task;

public class Task {
    private int id;
    private String title;
    private StatusTask statusTask;
    private String discrption;
    public Task(int id , String title , StatusTask statusTask , String discrption) {
        this.id = id;
        this.title = title;
        this.statusTask = statusTask;
        this.discrption = discrption;
    }
    public Task(String title , StatusTask statusTask , String discrption) {
        this.title = title;
        this.statusTask = statusTask;
        this.discrption = discrption;
    }

    public StatusTask getStatusTask() {
        return statusTask;
    }
    public void setStatusTask(StatusTask statusTask) {
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

    public String getDiscrption() {
        return discrption;
    }
    public void setDiscrption(String discrption) {
        this.discrption = discrption;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", statusTask='" + statusTask + '\'' +
                ", discrption='" + discrption + '\'' +
                '}';
    }
}
