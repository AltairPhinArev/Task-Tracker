package Manager;
import Task.Epic;
import Task.Subtask;
import Task.Task;

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


    public void removeAllTask(Task task) {
        taskById.clear();
        epicById.clear();
        subtaskById.clear();
    }

    public void crateTask(Task task) {
        task.setStatusTask("NEW");
        task.setId(idNewNum++);
        taskById.put(task.getId() , task);
    }
    public void updateTask(Task task){
        taskById.put(getIdNewNum() , task);
    }
    public void removeTask(Task task) {
        if(taskById.get(getIdNewNum()) != null) {
            taskById.remove(getIdNewNum());
        }
    }
    public void changeStatusTask(Task task) {
        if(task.getStatusTask().equals("NEW")) {
            task.setStatusTask("IN-PROGRESS");
        } else {
            task.setStatusTask("DONE");
        }
    }

    public void addEpic(Epic epic) {
        epic.setEpicStatus("NEW");
        epic.setId(idNewNum++);
        epicById.put(epic.getId() , epic);
    }
    public void updateEpic(Epic epic) {
        epicById.put(getIdNewNum() , epic);
    }

    public void updateEpicStatus(Epic epic, Subtask subtask) {
        for (int i = 0; i < subtaskById.size(); i++) {
            if (subtask.getSubtaskStatus().equals("DONE")) {
                epic.setEpicStatus("DONE");
            }
        }
    }

    public void printAllTask(Task task) { //вывод задач
        System.out.println(epicById.get(getIdNewNum()));
    }
    public HashMap printAllSubtask() { //вывод подзадач
        return subtaskById;
    }
    public HashMap printAllEpic() { //Выво эриков
        return epicById;
    }

    public Epic addSubtask(Subtask subtask , Epic epic) {
        subtask.setSubtaskStatus("NEW");
        epic.getSubtasksIds().add(subtask.getEpicId());
        subtask.setEpicId(epic.getId());
        return epic;
    }
    public void updateSubtaskStatus(Subtask subtask , Epic epic) {
        if(subtask.getSubtaskStatus().equals("NEW")) {
            subtask.setStatusTask("IN-PROGRESS");
        } else {
            subtask.setStatusTask("DONE");
        }
    }
    public void updateSubtataskTitle(Task task, Subtask subtask) {
        subtaskById.put(getIdNewNum() , subtask);
    }

}
