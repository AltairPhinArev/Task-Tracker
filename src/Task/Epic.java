package Task;
import java.util.ArrayList;

public class Epic extends Task {

    public Epic(int id, String title, String statusTask , String discrption) {
        super(id ,title, statusTask , discrption);
    }
    public Epic(String title, String statusTask , String discrption) {
        super(title, statusTask , discrption);
    }
    private ArrayList<Integer> subtasksIds = new ArrayList<>();
    public ArrayList<Integer> getSubtasksIds() {
        return subtasksIds;
    }
    public void setSubtasksIds(int id) {
        this.subtasksIds.add(id);
    }
}
