package Task;
import java.time.Duration;
import java.time.LocalDateTime;
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
    public Epic(String title, StatusTask statusTask, String discrption , LocalDateTime startTime , Duration duration) {
        super(title , statusTask , discrption  ,startTime ,Duration.ofMinutes(0));
    }
    public Epic(String title,  String discrption , LocalDateTime startTime , Duration duration) {
        super( title , discrption , startTime , Duration.ofMinutes(0));
    }

    public Epic() {

    }

    public ArrayList<Integer> subtasksIds = new ArrayList<>();

    public ArrayList<Integer> getSubtasksIds() {
        return subtasksIds;
    }
    public void setSubtasksIds(int id) {
        this.subtasksIds.add(id);
    }
}
