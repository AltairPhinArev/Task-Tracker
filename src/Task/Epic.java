package Task;
import java.util.ArrayList;

public class Epic extends Task {

    private String epicStatus;

    public Epic(int id, String title, String statusTask , String discrption) {
        super(id ,title, statusTask , discrption);
    }
    public Epic(String title, String statusTask , String discrption) {
        super(title, statusTask , discrption);
    }


    public String getEpicStatus() {
        return epicStatus;
    }
    public void setEpicStatus(String epicStatus) {
        this.epicStatus = epicStatus;
    }

    private ArrayList<Integer> subtasksIds;

    public ArrayList<Integer> getSubtasksIds() {
        return subtasksIds;
    }
    public void setSubtasksIds(ArrayList<Integer> subtasksIds) {
        this.subtasksIds = subtasksIds;
    }
}
