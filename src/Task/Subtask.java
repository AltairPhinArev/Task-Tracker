package Task;

public class Subtask extends Task {
    private int epicId;

    public Subtask(int epicId , int id ,String title, String statusTask, String discrption) {
        super(id, title, statusTask, discrption );
        this.epicId = epicId;
    }
    public Subtask(int epicId, String title, String statusTask, String discrption) {
        super(title, statusTask, discrption);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }
    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

}

