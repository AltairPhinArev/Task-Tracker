package Task;
import java.util.ArrayList;

public class Subtask extends Task {
    private int epicId;
    private String subtaskStatus;

    public String getSubtaskStatus() {
        return subtaskStatus;
    }
    public void setSubtaskStatus(String subtaskStatus) {
        this.subtaskStatus = subtaskStatus;
    }

    public int getEpicId() {
        return epicId;
    }
    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

}

