package Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;

public class Subtask extends Task {
    private int epicId;
    private LocalDateTime endTime;
    private LocalDateTime startTime;
    private Duration duration;

      public Subtask(int epicId, String title, StatusTask statusTask, String discrption , LocalDateTime startTime , Duration duration) {
        super(title, statusTask, discrption , startTime , duration);
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

}

