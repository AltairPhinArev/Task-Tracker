package Task;
import java.util.ArrayList;

public class Epic extends Task {
    public void removeSubId(int id) {
        for(int i = 0; i < getSubtasksIds().size(); i ++) {
            if(getSubtasksIds().get(i) == id) {
                getSubtasksIds().remove(i);
                break;
            }
        }
    }
    public Epic(int id, String title, StatusTask statusTask , String discrption) {
        super(id ,title, statusTask , discrption);
    }
    public Epic(String title, StatusTask statusTask , String discrption) {
        super(title, statusTask , discrption);
    }
    public ArrayList<Integer> subtasksIds = new ArrayList<>();
    public ArrayList<Integer> getSubtasksIds() {
        return subtasksIds;
    }
    public void setSubtasksIds(int id) {
        this.subtasksIds.add(id);
    }
}
