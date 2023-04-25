package Manager;

import Task.*;

import javax.naming.TimeLimitExceededException;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager extends Managers implements TaskManager {

    HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();

    public static int idNewNum = 1;

    public static HashMap<Integer,Task> taskById = new HashMap<>();
    public static HashMap<Integer,Epic> epicById = new HashMap<>();
    public static HashMap<Integer,Subtask> subtaskById = new HashMap<>();
    public TreeSet<Task> prioritizedTasks;
    public InMemoryTaskManager() {
        Comparator<Task> comparator = (task1, task2) -> {

            if (task1 == null && task2 == null) {
                return 0;

            } else if (task1 == null) {
                return 1;

            } else if (task2 == null) {
                    return -1;

            } else {

                if (task1.getStartTime().isAfter(task2.getStartTime())) {
                    return 1;

                } else if (task1.getStartTime().isBefore(task2.getStartTime())) {
                    return -1;

                } else {
                    return 0;
                }
            }
        };

        prioritizedTasks = new TreeSet<>(comparator);
    }

    @Override
    public void crateTask(Task task) throws IOException {
        try {
        taskById.put(idNewNum , task);
        task.setId(idNewNum++);
        task.setTypeTask(TypeTask.TASK);

            addPrioritizedTasks(task);
        } catch (TimeLimitExceededException exception) {
            System.out.println("TIME_ERROR");
        }
    }

    @Override
    public void updateTask(Task task) {
        if(taskById.get(task.getId()) != null) {
            taskById.put(task.getId(), task);
        }
    }

    @Override
    public void removeTaskById(int id) {
        inMemoryHistoryManager.remove(id); // под вопросм
        taskById.remove(id);
    }

    @Override
    public void removeAllTask() {
        taskById.clear();
        subtaskById.clear();
        epicById.clear();
    }

    @Override
    public void createEpic(Epic epic) throws IOException {
        epicById.put(idNewNum, epic);
        epic.setId(idNewNum++);
        updateEpicStatus(epic.getId());
        epic.setTypeTask(TypeTask.EPIC);
        try {
            calculateEpicStartAndEndTime(epic);
            addPrioritizedTasks(epic);
        } catch (TimeLimitExceededException exception) {
            System.out.println("Tfdgdfh");
        }

    }

    @Override
    public void updateEpic(Epic epic) {
        if(epicById.get(idNewNum) != null) {
            epicById.put(epic.getId() , epic);
        }
    }

    @Override
    public void updateEpicStatus(int id) {
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
    public void removeEpicById(int id) {
        Epic epic = epicById.get(id);
        for(Integer epId : epic.getSubtasksIds()) {
            inMemoryHistoryManager.remove(epId);
            subtaskById.remove(epId);
        }
        inMemoryHistoryManager.remove(id); // под вопросм
        epicById.remove(id);
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
    public void createSubTask(Subtask subtask) throws IOException {
        try {
            addPrioritizedTasks(subtask);
            subtask.setId(idNewNum++);
            subtaskById.put(subtask.getId() , subtask);
            Epic epic = epicById.get(subtask.getEpicId());
            epic.setSubtasksIds(subtask.getId());
            epicById.put(subtask.getEpicId() , epic);
            updateEpicStatus(subtask.getEpicId());
            subtask.setTypeTask(TypeTask.SUBTASK);
        } catch (TimeLimitExceededException exception) {
            System.out.println("Tfdgdfh");
        }
    }

    @Override
    public void updateSubtaskTitle(Subtask subtask) {
        if(subtaskById.get(subtask.getId()) != null ) {
            subtaskById.put(subtask.getId() , subtask);
            updateEpicStatus(subtask.getEpicId());
        }
    }

    @Override
    public Task printTaskById(int id) {
        inMemoryHistoryManager.addHistory(taskById.get(id));
        return taskById.get(id);
    }

    @Override
    public Epic printEpicById(int id) {
        updateEpicStatus(id);
        inMemoryHistoryManager.addHistory(epicById.get(id));
        return epicById.get(id);
    }

    @Override
    public Subtask printSubtaskById(int id) {
        inMemoryHistoryManager.addHistory(subtaskById.get(id));
        return subtaskById.get(id);
    }

    @Override
    public void removeSubTaskById(int id) {
        Subtask subtask = subtaskById.get(id);
        Epic epic = epicById.get(subtask.getEpicId());
        inMemoryHistoryManager.remove(id);

        epic.removeSubId(id);
        subtaskById.remove(id);
        updateEpicStatus(subtask.getEpicId());
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
    public List<Task> getHistory() throws IOException {
        return inMemoryHistoryManager.getHistory();
    }


    public void addPrioritizedTasks(Task task) throws TimeLimitExceededException {
        for (Task taskTime : getPrioritizedTasks()) {
            if ((taskTime.getStartTime().isBefore(task.getStartTime()) &&
                    taskTime.getEndTime().isAfter(task.getStartTime())) ||
                    (taskTime.getStartTime().isBefore(task.getEndTime()) &&
                            taskTime.getEndTime().isAfter(task.getEndTime()))) {
                throw new TimeLimitExceededException("Время выполнения задач не может пересекаться");
            }
        }
        getPrioritizedTasks().add(task);
    }
    public TreeSet<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }

    public void calculateEpicStartAndEndTime(Epic epic) {
        LocalDateTime earliestSubtaskStart = LocalDateTime.MAX;
        LocalDateTime latestSubtaskEnd = LocalDateTime.MIN;

        if (epic.getSubtasksIds() != null) {
            for (Integer id : epic.getSubtasksIds()) {
                Subtask subtask = subtaskById.get(id);
                if (subtask.getStartTime().isBefore(earliestSubtaskStart)) {
                    earliestSubtaskStart = subtask.getStartTime();
                }
                if (subtask.getEndTime().isAfter(latestSubtaskEnd)) {
                    latestSubtaskEnd = subtask.getEndTime();
                }
            }
            if (epic.getStartTime() == null) {
                epic.setStartTime(earliestSubtaskStart);
            }
            if (latestSubtaskEnd.isAfter(epic.getStartTime())) {
                epic.setEndTime(latestSubtaskEnd);
            } else {
                epic.setEndTime(epic.getStartTime());
            }
            Duration duration = Duration.between(epic.getStartTime(), epic.getEndTime());
            epic.setDuration(duration);
        } else {
            if (epic.getStartTime() == null) {
                epic.setStartTime(LocalDateTime.now());
            }
            epic.setEndTime(epic.getStartTime());
            epic.setDuration(Duration.ZERO);
        }
    }
}