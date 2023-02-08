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

    public HashMap<Integer, Task> getTaskById() {
        return taskById;
    }
    public HashMap<Integer, Subtask> getSubtaskById() {
        return subtaskById;
    }
    public HashMap<Integer, Epic> getEpicById() {
        return epicById;
    }

    public void removeAllTask() {
        taskById.clear();
        epicById.clear();
        subtaskById.clear();
    }

    public void crateTask(Task task) {
        task.setId(idNewNum);
        taskById.put(idNewNum , task);
        idNewNum++;
    }
    public void updateTask(Task task) {
        if(taskById.get(task.getId()) != null) {
            taskById.put(task.getId(), task);
        }
    }
    public void removeTask(int idNewNum) {
        if(taskById.get(idNewNum) != null) {
            taskById.remove(idNewNum);
        }
    }
    public void createEpic(Epic epic) {
        epic.setId(idNewNum);
        epicById.put(epic.getId() , epic);
        idNewNum++;
    }
    public void updateEpic(Epic epic) {
        if(epicById.get(idNewNum) != null) {
            epicById.put(epic.getId() , epic);
        }
    }
    private void updEpicStatus(Epic epic) {
        boolean nEw = true;
        boolean done = true;
        if(epic.getSubtasksIds().isEmpty()) {
            epic.setStatusTask("NEW");
        } else {
            for(int subId : epic.getSubtasksIds()) {
                if(epic.getEpicStatus().equals("NEW")) {
                    boolean chekNew = epic.getEpicStatus().equals(subtaskById.get(subId).getStatusTask());
                    nEw = nEw && chekNew;
                }
                if(epic.getEpicStatus().equals("DONE")) {
                    boolean chekDone = epic.getEpicStatus().equals(subtaskById.get(subId).getStatusTask());
                    done = done && chekDone;
                }
            }
            if(nEw == true) {
                epic.setStatusTask("NEW");
            } else if (done == true) {
                epic.setStatusTask("DONE");
            } else {
                epic.setStatusTask("IN-PROGRESS");
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

    public void updateSubTask(Subtask task) {
        subtaskById.put(task.getId(), task);
        updEpicStatus(epicById.get(task.getEpicId()));
    }
    public void createSubTask(Subtask subTask) {
        subTask.setId(idNewNum);
        updateSubTask(subTask);
        epicById.get(subTask.getEpicId()).getSubtasksIds().add(subTask.getId());
        idNewNum++;
    }
    public void updateSubtataskTitle(Subtask subtask , Epic epic) {
        if(subtaskById.get(idNewNum) != null ) {
            subtaskById.put(idNewNum , subtask);
            updEpicStatus(epic);
        }
    }

    // Получение задачи по его id
    public void printTaskById(int idNewNum){
        if(taskById.get(idNewNum) != null) {
            System.out.println(taskById.get(idNewNum));
        }
    }
    public void printEpicById(int idNewNum) {
        if(epicById.get(idNewNum) != null) {
                System.out.println(epicById.get(idNewNum));
        }
    }
    public void printSubtaskById(int idNewNum ) {
        if(subtaskById.get(idNewNum) != null) {
            System.out.println(subtaskById.get(idNewNum));
        }
    }
    //Удаление по Id
    public void removeTaskById(int idNewNum) {
        taskById.remove(idNewNum);
    }
    public void removeEpicById(int idNewNum) {
        epicById.remove(idNewNum);
    }
    public void removeSubTaskById(int idNewNum , Epic epic) {
        subtaskById.remove(idNewNum);
        updEpicStatus(epic);
    }
    public void printAllSubtaskByEpic(Epic epic , Subtask subtask) {
        if(epic.getSubtasksIds().size() > 0) {
            for(Integer id :epic.getSubtasksIds()) {
                System.out.println(subtaskById.get(id));
            }
        }
    }
}
/*
        subtask.setId(epicId);
        subtaskById.put(subtask.getId() ,subtask);
        Epic epic = epicById.get(subtask.getId());

        subtask.setId(idNewNum);
        epic.getSubtasksIds().add(subtask.getEpicId());
        subtask.setEpicId(epic.getId());
        subtaskById.put(epic.getId() , subtask);
        epic = epicById.get(subtask.getId());
        updEpicStatus(epic);
        idNewNum++;
 */