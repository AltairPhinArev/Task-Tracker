package Manager;
import Task.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager extends Managers implements TaskManager {

    HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();
    private int idNewNum = 1;

    private HashMap<Integer , Task> taskById = new HashMap<>();
    private HashMap<Integer , Epic> epicById = new HashMap<>();
    private HashMap<Integer , Subtask> subtaskById = new HashMap<>();

    @Override
    public void removeAllTask() {
        taskById.clear();
    }

    @Override
    public void crateTask(Task task) {
        taskById.put(idNewNum , task);
        task.setId(idNewNum++);
    }

    @Override
    public void updateTask(Task task) {
        if(taskById.get(task.getId()) != null) {
            taskById.put(task.getId(), task);
        }
    }

    @Override
    public void removeTask(int id) {
        if(taskById.get(id) != null) {
            taskById.remove(id);
        }
    }

    @Override
    public void createEpic(Epic epic) {
        epicById.put(idNewNum, epic);
        epic.setId(idNewNum++);
    }

    @Override
    public void updateEpic(Epic epic) {
        if(epicById.get(idNewNum) != null) {
            epicById.put(epic.getId() , epic);
        }
    }

    @Override
    public void updEpicStatus(int id) {
        int nEw = 0;
        int  done = 0;
        Epic epic = epicById.get(id);
        if(epic.getSubtasksIds().isEmpty()) {
            epic.setStatusTask(StatusTask.NEW);
        } else {
            for(Integer subId : epic.getSubtasksIds()) {
                if((subtaskById.get(subId).getStatusTask()).equals(StatusTask.NEW)) {
                    nEw++;
                }
                if((subtaskById.get(subId).getStatusTask()).equals(StatusTask.DONE)) {
                    done++;
                }
            }
            if(nEw == epic.getSubtasksIds().size()) {
                epic.setStatusTask(StatusTask.NEW);
                epicById.put(epic.getId(), epic);
            } else if (done == epic.getSubtasksIds().size()) {
                epic.setStatusTask(StatusTask.DONE);
                epicById.put(epic.getId(), epic);
            } else {
                epic.setStatusTask(StatusTask.IN_PROGRESS);
                epicById.put(epic.getId(), epic);
            }
        }
    }

    @Override
    public ArrayList<Task> printAllTask() { //вывод задач
        return new ArrayList<>(taskById.values());
    }

    @Override
    public ArrayList<Subtask> printAllSubtask() { //вывод подзадач
        return new ArrayList<>(subtaskById.values());
    }

    @Override
    public ArrayList<Epic> printAllEpic() { //Выво эриков
        return new ArrayList<>(epicById.values());
    }

    @Override
    public void createSubTask(Subtask subtask) {
        subtask.setId(idNewNum++);
        subtaskById.put(subtask.getId() , subtask);
        Epic epic = epicById.get(subtask.getEpicId());
        epic.setSubtasksIds(subtask.getId());
        epicById.put(subtask.getEpicId() , epic);
        updEpicStatus(subtask.getEpicId());
    }

    @Override
    public void updateSubtataskTitle(Subtask subtask) {
        if(subtaskById.get(subtask.getId()) != null ) {
            subtaskById.put(subtask.getId() , subtask);
            updEpicStatus(subtask.getEpicId());
        }
    }

    @Override
    public Task printTaskById(int id) {
        inMemoryHistoryManager.addHistory(taskById.get(id));
        return taskById.get(id);
    }

    @Override
    public Epic printEpicById(int id) {
        inMemoryHistoryManager.addHistory(epicById.get(id));
        return epicById.get(id);
    }

    @Override
    public Subtask printSubtaskById(int id ) {
        inMemoryHistoryManager.addHistory(subtaskById.get(id));
        return subtaskById.get(id);
    }

    @Override
    public void removeTaskById(int id) {
        inMemoryHistoryManager.updateHistory(id);
        taskById.remove(id);
    }

    @Override
    public void removeEpicById(int id) {
        Epic epic = epicById.get(id);
        for(Integer epId : epic.getSubtasksIds()) {
            subtaskById.remove(epId);
        }
        inMemoryHistoryManager.updateHistory(id);
        epicById.remove(id);
    }

    @Override
    public void removeSubTaskById(int id) {
        Subtask subtask = subtaskById.get(id);
        Epic epic = epicById.get(subtask.getEpicId());
        inMemoryHistoryManager.updateHistory(id);
        epic.removeSubId(id);
        subtaskById.remove(id);
        updEpicStatus(subtask.getEpicId());
    }

    @Override
    public ArrayList<Subtask> printAllSubtaskByEpic(Epic epic) {
        ArrayList<Subtask> subtasks = new ArrayList<>();
        if(epic.getSubtasksIds().size() > 0) {
            for(Integer id :epic.getSubtasksIds()) {
                subtasks.add(subtaskById.get(id));
            }
        }
        return subtasks;
    }
    @Override
    public List<Task> getHistory() {
        return inMemoryHistoryManager.getHistory();
    }
}
