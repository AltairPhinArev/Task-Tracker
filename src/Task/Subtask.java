package Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;

public class Subtask extends Task {
    private int epicId;
    private LocalDateTime endTime;
    private LocalDateTime startTime;
    private Duration duration;

      public Subtask(int epicId, String title, StatusTask statusTask, String description , LocalDateTime startTime , Duration duration) {
        super(title, statusTask, description , startTime , duration);
        this.epicId = epicId;
    }

    public Subtask() {

    }

    public int getEpicId() {
        return epicId;
    }
    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public LocalDateTime getStartTime() {
        return startTime;
    }

    @Override
    public Duration getDuration() {
        return duration;
    }
}

