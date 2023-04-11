package Task;


import java.time.Duration;
import java.time.LocalDateTime;

public class Task {
    private String discrption;
    private int id;
    private String title;
    private TypeTask typeTask;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Duration duration;
    private StatusTask statusTask;

    public Task(String title , StatusTask statusTask , String discrption , LocalDateTime startTime , Duration duration) {
        this.title = title;
        this.statusTask = statusTask;
        this.discrption = discrption;
        this.startTime = startTime;
        this.duration = duration;
        endTime =getEndTime();
    }

    public Task(String title , String discrption , LocalDateTime startTime , Duration duration) {
        this.title = title;
        this.discrption = discrption;
        this.startTime = startTime;
        this.duration= duration;
        endTime = getEndTime();
    }

    public Task() {

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
    public TypeTask getTypeTask() {
        return typeTask;
    }
    public void setTypeTask(TypeTask typeTask) {
        this.typeTask = typeTask;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getEndTime() {
        if (startTime != null && duration != null) {
            return startTime.plusMinutes(duration.toMinutes());
        } else {
            return null;
        }
    }
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", typeTask=" + typeTask +
                ", startTime=" + startTime +
                ", duration=" + duration +
                ", statusTask=" + statusTask +
                ", discrption='" + discrption + '\'' +
                '}';
    }
}