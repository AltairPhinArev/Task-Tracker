package Task;
import com.google.gson.annotations.SerializedName;

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
    @SerializedName("end_time")
    private LocalDateTime endTime;

    public Epic(String title, StatusTask statusTask, String discription , LocalDateTime startTime , Duration duration) {
        super(title , statusTask , discription  ,startTime ,Duration.ofMinutes(0));
    }

    public Epic(String title,  String discription ) {
        super( title , discription );
        getEndTime();
    }


    public Epic(String title,  String discription , LocalDateTime startTime , Duration duration) {
        super( title , discription , startTime , Duration.ofMinutes(0));
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

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

}
