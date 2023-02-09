package Manager;
import Task.*;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int idNewNum = 1;

    private HashMap<Integer , Task> taskById = new HashMap<>();
    private HashMap<Integer , Epic> epicById = new HashMap<>();
    private HashMap<Integer , Subtask> subtaskById = new HashMap<>();

    public void removeAllTask() {
        taskById.clear();
    }

    public void crateTask(Task task) {
        taskById.put(idNewNum , task);
        task.setId(idNewNum++);
    }
    public void updateTask(Task task) {
        if(taskById.get(task.getId()) != null) {
            taskById.put(task.getId(), task);
        }
    }
    public void removeTask(int id) {
        if(taskById.get(id) != null) {
            taskById.remove(id);
        }
    }
    public void createEpic(Epic epic) {
            epicById.put(idNewNum, epic);
            epic.setId(idNewNum++);
    }
    public void updateEpic(Epic epic) {
        if(epicById.get(idNewNum) != null) {
            epicById.put(epic.getId() , epic);
        }
    }
    public void updEpicStatus(int id) {
        int nEw = 0;
        int  done = 0;
        Epic epic = epicById.get(id);
           if(epic.getSubtasksIds().isEmpty()) {
               epic.setStatusTask("NEW");
           } else {
            for(Integer subId : epic.getSubtasksIds()) {
                if((subtaskById.get(subId).getStatusTask()).equals("NEW")) {
                   nEw++;
                }
                if((subtaskById.get(subId).getStatusTask()).equals("DONE")) {
                    done++;
                }
            }
            if(nEw == epic.getSubtasksIds().size()) {
                epic.setStatusTask("NEW");
                epicById.put(epic.getId(), epic);
            } else if (done == epic.getSubtasksIds().size()) {
                epic.setStatusTask("DONE");
                epicById.put(epic.getId(), epic);
            } else {
                epic.setStatusTask("IN-PROGRESS");
                epicById.put(epic.getId(), epic);
            }
        }
    }

    public ArrayList<Task> printAllTask() { //вывод задач
        return new ArrayList<>(taskById.values());
        }

    public ArrayList<Subtask> printAllSubtask() { //вывод подзадач
        return new ArrayList<>(subtaskById.values());
    }
    public ArrayList<Epic> printAllEpic() { //Выво эриков
        return new ArrayList<>(epicById.values());
    }

    public void createSubTask(Subtask subtask) {
        subtask.setId(idNewNum++);
        subtaskById.put(subtask.getId() , subtask);
        Epic epic = epicById.get(subtask.getEpicId());
        epic.setSubtasksIds(subtask.getId());
        epicById.put(subtask.getEpicId() , epic);
        updEpicStatus(subtask.getEpicId());
    }
    public void updateSubtataskTitle(Subtask subtask) {
        if(subtaskById.get(subtask.getId()) != null ) {
            subtaskById.put(subtask.getId() , subtask);
            updEpicStatus(subtask.getEpicId());
        }
    }

    // Получение задачи по его id
    public Task printTaskById(int id) {
        return taskById.get(id);
    }
    public Epic printEpicById(int id) {
        return epicById.get(id);
    }
    public Subtask printSubtaskById(int id ) {
        return subtaskById.get(id);
    }
    //Удаление по Id
    public void removeTaskById(int id) {
        taskById.remove(id);
    }
    public void removeEpicById(int id) {
        Epic epic = epicById.get(id);
        for(Integer epId : epic.getSubtasksIds()) {
            subtaskById.remove(epId);
        }
            epicById.remove(id);
    }
    public void removeSubTaskById(int id) {
        Subtask subtask = subtaskById.get(id);
        Epic epic = epicById.get(subtask.getEpicId());
        epic.removeSubId(id);
        subtaskById.remove(id);
        updEpicStatus(subtask.getEpicId());
    }
    public ArrayList<Subtask> printAllSubtaskByEpic(Epic epic) {
        ArrayList<Subtask> subtasks = new ArrayList<>();
        if(epic.getSubtasksIds().size() > 0) {
            for(Integer id :epic.getSubtasksIds()) {
                 subtasks.add(subtaskById.get(id));
            }
        }
        return subtasks;
    }
}
