package Manager;

import Task.*;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class FileBackedTasksManager extends InMemoryTaskManager {

    private File file;
    public FileBackedTasksManager(File file) {
        this.file = file;
    }
    private static int updateIdTask(int id) {
        if (id >= idNewNum) {
            idNewNum = id + 1;
        }
        return idNewNum;
    }
    public FileBackedTasksManager() {

    }

    public static FileBackedTasksManager loadFromFile(File file) throws ManagerSaveException {
        try {
            FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
            HistoryManager historyManager = Managers.getDefaultHistory();

            Reader fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            Task task = null;
            String line = br.readLine();
            boolean isHeading = true;
            while (br.ready()) {
                if (isHeading) {
                    isHeading = false;
                } else {
                    line = br.readLine();
                    String[] taskFromLine = line.split(",");
                    if (line.isBlank()) {
                        line = br.readLine();
                        if (!line.isBlank()) {
                            List<Task> hisoryTask = historyFromId(historyFromString(line));
                           for (int i = 0; i < hisoryTask.size(); i++) {
                                historyManager.addHistory(hisoryTask.get(i));
                           }
                            fileBackedTasksManager.getHistory().addAll(historyManager.getHistory());
                            break;
                        } else {
                            break;
                        }
                    }
                    switch (taskFromLine[1]) {
                        case "TASK":
                            if (line.isBlank()) {
                                break;
                            } else {
                                task = new Task();
                                break;
                            }
                        case "EPIC":
                            if (line.isBlank()) {
                                break;
                            } else {
                                task = new Epic();
                                break;
                            }
                        case "SUBTASK":
                            if (line.isBlank()) {
                                break;
                            } else {
                                task = new Subtask();
                                break;
                            }
                    }

                    task.setId(Integer.parseInt(taskFromLine[0]));
                    task.setTitle(taskFromLine[2]);
                    task.setStatusTask(StatusTask.valueOf(taskFromLine[3]));
                    task.setDescription(taskFromLine[4]);
                    task.setStartTime(LocalDateTime.parse(taskFromLine[5]));
                    task.setDuration(Duration.parse(taskFromLine[6]));

                    if (task.getClass() == Task.class) {
                        updateIdTask(Integer.parseInt(taskFromLine[0]));
                        taskById.put(task.getId(), task);

                    } else if (task.getClass() == Epic.class) {
                        updateIdTask(Integer.parseInt(taskFromLine[0]));
                        epicById.put(task.getId(),  (Epic) task);

                    } else if (task.getClass() == Subtask.class) {

                        Subtask subtask = (Subtask) task;
                        subtask.setEpicId(Integer.parseInt(taskFromLine[7]));
                        updateIdTask(Integer.parseInt(taskFromLine[0]));
                        subtaskById.put(subtask.getId(), subtask);
                        epicById.get(subtask.getEpicId()).getSubtasksIds().add(subtask.getId());
                    }
                }
            }
            br.close();
            return fileBackedTasksManager;
        } catch (IOException exception) {
            throw new ManagerSaveException(exception.getMessage());
        }

    }

    private static List<Task> historyFromId(List<Integer> historyId) {

        List<Task> historyFullData = new ArrayList<>();
        for (Integer integer : historyId) {
            if (taskById.containsKey(integer)) {
                historyFullData.add(taskById.get(integer));
            } else if (epicById.containsKey(integer)) {
                historyFullData.add(epicById.get(integer));
            } else if (subtaskById.containsKey(integer)) {
                historyFullData.add(subtaskById.get(integer));
            }
        }
        return historyFullData;
    }

        private static List<Integer> historyFromString(String value)  {
            List<Integer> history = new ArrayList<>();
            String[] items = value.split(",");
            for (int i = 0; i < items.length; i++) {
                int id = Integer.parseInt(items[i]);
                history.add(id);
            }
            return history;
        }

    private static String historyToString(HistoryManager manager) {
            String historyLine = "";
            for(int i = 0; i < manager.getHistory().size(); i++) {
                historyLine = String.valueOf(manager.getHistory().get(i).getId());
                historyLine = "," + historyLine;
        }
                return historyLine;
    }

    private String epicToString(Epic epic) {

        return epic.getId() + "," +
                epic.getTypeTask() + "," +
                epic.getTitle() + "," +
                epic.getStatusTask() + "," +
                epic.getDescription() + "," +
                epic.getStartTime() + "," +
                epic.getDuration() + "," +
                epic.getSubtasksIds();
    }

    private String taskToString(Task task) {

        return task.getId() + "," +
                TypeTask.TASK + "," +
                task.getTitle() + "," +
                task.getStatusTask().toString() + "," +
                task.getDescription() + "," +
                task.getStartTime() + "," +
                task.getDuration();
    }

    private String subtaskToString(Subtask subtask) {

        return subtask.getId() + "," +
                subtask.getTypeTask() + "," +
                subtask.getTitle() + "," +
                subtask.getStatusTask() + "," +
                subtask.getDescription() + "," +
                subtask.getStartTime() + "," +
                subtask.getDuration() + "," +
                subtask.getEpicId();
    }

    public void save() throws IOException {
        try {
            FileWriter fileWriter = new FileWriter("Resources.csv");
            fileWriter.write("id,type,name,status,description,time,duration,epic\n");

            for (Task task : taskById.values()) {
                fileWriter.write(taskToString(task) + "\n");
            }
            for (Epic epic : epicById.values()) {
                fileWriter.write(epicToString(epic) + "\n");
            }
            for (Subtask subtask : subtaskById.values()) {
                fileWriter.write(subtaskToString(subtask) + "\n");
            }

            fileWriter.write("\n");
            fileWriter.write(historyToString(inMemoryHistoryManager));
            fileWriter.write("\n");
            fileWriter.close();

        } catch (IOException exception) {
            throw new ManagerSaveException("Error task has not been saved");
        }
    }

    @Override
    public void removeAllTask() {
        super.removeAllTask();
    }

    @Override
    public void crateTask(Task task) throws IOException {
        super.crateTask(task);
        save();
    }

    @Override
    public void updateEpicStatus(int id) {
        super.updateEpicStatus(id);
    }


    @Override
    public ArrayList<Epic> printAllEpic() {
        return super.printAllEpic();
    }

    @Override
    public Task printTaskById(int id) {
        return super.printTaskById(id);
    }

    @Override
    public Epic printEpicById(int id) {
        return super.printEpicById(id);
    }

    @Override
    public Subtask printSubtaskById(int id) {
        return super.printSubtaskById(id);
    }

    @Override
    public void removeTaskById(int id) {
        super.removeTaskById(id);
    }

    @Override
    public void removeEpicById(int id) {
        super.removeEpicById(id);
    }

    @Override
    public void removeSubTaskById(int id) {
        super.removeSubTaskById(id);
    }

    @Override
    public List<Task> getHistory() throws IOException {
        return super.getHistory();
    }
    @Override
    public ArrayList<Subtask> printAllSubtaskByEpic(Epic epic) {
        return super.printAllSubtaskByEpic(epic);
    }

    @Override
    public void updateSubtaskTitle(Subtask subtask) {
        super.updateSubtaskTitle(subtask);
    }

    @Override
    public void createSubTask(Subtask subtask) throws IOException {
        super.createSubTask(subtask);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
    }

    @Override
    public void createEpic(Epic epic) throws IOException {
        super.createEpic(epic);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
    }

}