package Task;

import com.google.gson.annotations.SerializedName;

import java.time.Duration;
import java.time.LocalDateTime;


public class Subtask extends Task {
    private int epicId;
    @SerializedName("ENDTIMESUB")
    private LocalDateTime endTime;
    @SerializedName("START TIMES UB")
    private LocalDateTime startTime;
    @SerializedName("DURATIONS UB")
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
}

