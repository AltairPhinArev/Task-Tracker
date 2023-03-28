package Manager;

import Task.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class FileBackedTasksManager extends InMemoryTaskManager {
    public File file;
    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public static FileBackedTasksManager loadFromFile(File file) throws ManagerSaveException {
        try {
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
                        break;
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

                    if (!line.isBlank()) {
                        task.setId(Integer.parseInt(taskFromLine[0]));
                        task.setTitle(taskFromLine[2]);
                        task.setStatusTask(StatusTask.valueOf(taskFromLine[3]));
                        task.setDiscrption(taskFromLine[4]);

                        if (task.getClass() == Task.class) {
                            if (task.getId() == idNewNum) {
                                taskById.put(task.getId(), task);
                                idNewNum++;
                            } else if (task.getId() > idNewNum) {
                                taskById.put(task.getId(), task);
                                idNewNum = task.getId() + 1;
                            } else {
                                idNewNum = task.getId();
                                taskById.put(task.getId(), task);
                                idNewNum++;
                            }
                        } else if (task.getClass() == Epic.class) {
                            if (task.getId() == idNewNum) {
                                epicById.put(task.getId(),  (Epic) task);
                                idNewNum++;
                            } else if (task.getId() > idNewNum) {
                                epicById.put(task.getId(),  (Epic) task);
                                idNewNum = task.getId() + 1;
                            } else {
                                idNewNum = task.getId();
                                epicById.put(task.getId(),  (Epic) task);
                                idNewNum++;
                            }
                        } else if (task.getClass() == Subtask.class) {
                            Subtask subtask = (Subtask) task;
                            subtask.setEpicId(Integer.parseInt(taskFromLine[5]));
                            if (subtask.getId() == idNewNum) {
                                subtaskById.put(subtask.getId(), subtask);
                                idNewNum++;
                            } else if (subtask.getId() > idNewNum) {
                                idNewNum = subtask.getId() + 1;
                                subtaskById.put(subtask.getId(), subtask);
                            } else {
                                subtaskById.put(subtask.getId(), subtask);
                                idNewNum = Integer.parseInt(taskFromLine[0]);
                            }
                            epicById.get(subtask.getEpicId()).getSubtasksIds().add(subtask.getId());
                        }
                        } else {
                            if (line.isBlank()) {
                                br.readLine();
                                String[] historyString = line.split(",");
                                for (int i = 0; i < historyString.length; i++) {
                                 Managers.getDefaultHistory().getHistory().addAll(
                                 historyFromId(historyFromString(historyString[i] , Managers.getDefaultFile(file))));
                            }
                        }
                    }
                }
            }
            br.close();
            return new FileBackedTasksManager(file);
        } catch (IOException exception) {
            throw new ManagerSaveException(exception.getMessage());
        }
    }

    public static List<Task> historyFromId(List<Integer> historyId) {

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

        static List<Integer> historyFromString(String value, FileBackedTasksManager manager)  {
            List<Integer> history = new ArrayList<>();
            String[] items = value.split(",");
            for (int i = 0; i < items.length; i++) {
                int id = Integer.parseInt(items[i]);
                history.add(id);
            }
            return history;
        }

    public static String historyToString(HistoryManager manager) {
            String historyLine = "";
            for(int i = 0; i < manager.getHistory().size(); i++) {
                historyLine = String.valueOf(manager.getHistory().get(i).getId());
                historyLine = "," + historyLine;
        }
                return historyLine;
    }

    public String epicToString(Epic epic) {

        return epic.getId() + "," +
                epic.getTypeTask() + "," +
                epic.getTitle() + "," +
                epic.getStatusTask() + "," +
                epic.getDiscrption() + "," +
                epic.getSubtasksIds();
    }

    public String taskToString(Task task) {

        return task.getId() + "," +
                TypeTask.TASK + "," +
                task.getTitle() + "," +
                task.getStatusTask().toString() + "," +
                task.getDiscrption();
    }

    public String subtaskToString(Subtask subtask) {

        return subtask.getId() + "," +
                subtask.getTypeTask() + "," +
                subtask.getTitle() + "," +
                subtask.getStatusTask() + "," +
                subtask.getDiscrption() + "," +
                subtask.getEpicId();
    }

    public void save() throws IOException {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("id,type,name,status,description,epic \n");

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