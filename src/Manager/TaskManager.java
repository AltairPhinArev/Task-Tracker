package Manager;
import Task.*;
import java.util.HashMap;

public class TaskManager {
    private int idNewNum = 1;
    public int getIdNewNum() {
        return idNewNum;
    }
    public void setIdNewNum(int idNewNum) {
        this.idNewNum = idNewNum;
    }

    private HashMap<Integer , Task> taskById = new HashMap<>();
    private HashMap<Integer , Epic> epicById = new HashMap<>();
    private HashMap<Integer , Subtask> subtaskById = new HashMap<>();

    public void removeAllTask() {
        taskById.clear();
        epicById.clear();
        subtaskById.clear();
    }

    public void crateTask(Task task) {
        task.setId(idNewNum);
        taskById.put(idNewNum , task);
        idNewNum = idNewNum + 1;
    }
    public void updateTask(int idNewNum, Task task) {
        if(taskById.get(idNewNum).equals(task.getTitle())) {
            taskById.put(idNewNum , task);
        }
    }
    public void removeTask(int idNewNum) {
        if(taskById.get(idNewNum) != null) {
            taskById.remove(idNewNum);
        }
    }
    public void addEpic(Epic epic) {
        epic.setId(idNewNum);
        epicById.put(epic.getId() , epic);
        idNewNum++;
    }
    public void updateEpic(Epic epic) {
        if(epicById.get(idNewNum) != null) {
            epicById.put(idNewNum , epic);
        }
    }

    private void updateEpicStatus(Epic epic , Subtask subtask) {
        if(subtask.getEpicId() == epic.getId()) {
            for (int i = 0; i < subtaskById.size(); i++) {
                if (subtask.getStatusTask().equals("NEW")) {
                    epic.setStatusTask("NEW");
                } else if (subtask.getStatusTask().equals("IN-PROGRESS")) {
                    epic.setStatusTask("IN-PROGRESS");
                } else {
                    epic.setEpicStatus("DONE");
                }
            }
        }
    }

    public void printAllTask() { //вывод задач
        for(Task task : getTaskById().values()) {
            System.out.println(task);
        }
    }
    public void printAllSubtask() { //вывод подзадач
        for(Subtask subtask : getSubtaskById().values()) {
            System.out.println(subtask);
        }
    }
    public void printAllEpic() { //Выво эриков
        for(Epic epic : getEpicById().values()) {
            System.out.println(epic);
        }
    }

    public void addSubtask(Subtask subtask , Epic epic) {
        epic.getSubtasksIds().add(subtask.getEpicId());
        subtask.setEpicId(epic.getId());
    }
    public void updateSubtataskTitle(int idNewNum , Subtask subtask , Epic epic) {
        if(subtaskById.get(idNewNum) != null ) {
            subtaskById.put(idNewNum , subtask);
        }
        updateEpicStatus(epic ,subtask);
    }

    // Получение задачи по его id
    public void printTaskById(int idNewNum){
        System.out.println(taskById.get(idNewNum));
    }
    public void printEpicById(int idNewNum) {
        System.out.println(epicById.get(idNewNum));
    }
    public void printSubtaskById(int idNewNum) {
        System.out.println(subtaskById.get(idNewNum));
    }
    //Удаление по Id
    public void removeTaskById(int idNewNum) {
        taskById.remove(idNewNum);
    }
    public void removeEpicById(int idNewNum) {
        epicById.remove(idNewNum);
    }
    public void removeSubTaskById(int idNewNum){
        subtaskById.remove(idNewNum);
    }
    public void removeTasksByStatus(int idNewNum , Subtask subtask, Epic epic , Task task) {
        if (task.getStatusTask().equals(epic.getStatusTask()) && subtaskById.equals(subtask.getStatusTask())) {
            if (!(taskById.isEmpty()) && !(epicById.isEmpty()) && !(subtaskById.isEmpty())) {
                taskById.remove(idNewNum);
                epicById.remove(idNewNum);
                subtaskById.remove(idNewNum);
            }
        }
    }

    public HashMap<Integer, Epic> getEpicById() {
        return epicById;
    }

    public HashMap<Integer, Task> getTaskById() {
        return taskById;
    }

    public HashMap<Integer, Subtask> getSubtaskById() {
        return subtaskById;
    }
}
