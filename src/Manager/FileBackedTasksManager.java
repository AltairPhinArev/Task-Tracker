package Manager;

import Task.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {

    public static void loadFromFile() throws IOException {

        Reader fileReader = new FileReader("Resources.csv");
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
                if(taskFromLine.length < 4) {
                    break;
                }

                switch (taskFromLine[1]) {
                    case "TASK":
                        task = new Task();
                        break;
                    case "EPIC":
                        task = new Epic();
                        break;
                    case "SUBTASK":
                        task = new Subtask();
                        break;
                }

                task.setId(Integer.parseInt(taskFromLine[0]));
                task.setTitle(taskFromLine[2]);
                task.setStatusTask(StatusTask.valueOf(taskFromLine[3]));
                task.setDiscrption(taskFromLine[4]);

                if (task.getClass() == Task.class) {
                    taskById.put(task.getId(), task);
                } else if (task.getClass() == Epic.class) {
                    epicById.put(task.getId(), (Epic) task);
                } else if (task.getClass() == Subtask.class){
                    Subtask subtask = (Subtask) task;
                    subtask.setEpicId(Integer.parseInt(taskFromLine[5]));
                    subtaskById.put(subtask.getId(), subtask);
                    epicById.get(subtask.getEpicId()).getSubtasksIds().add(subtask.getId());
                } else {
                    historyFromString(historyToString(getDefaultHistory()));
                }

            }
        }
        br.close();
    }

    static List<Integer> historyFromString(String value) throws IOException {
        HistoryManager manager = Managers.getDefaultHistory();
        List<Integer> idHistory = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader("Resources.csv"));
        String line;
        boolean isHistoryTask = false;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.equals("History Task")) {
                isHistoryTask = true;
                continue;
            }
            if (isHistoryTask) {
                String[] historyValues = line.split(",");
                if (historyValues.length >= 3) {
                    for (int i = 0; i < manager.getHistory().size(); i++) {
                        if (historyValues[0].equals(String.valueOf(manager.getHistory().get(i).getId()))) {
                            idHistory.add(Integer.parseInt(historyValues[0]));
                        }
                    }
                }
            }
        }
        bufferedReader.close();
        return idHistory;
    }

    public static String historyToString(HistoryManager manager) throws IOException {
        try {
            FileWriter fileWriter = new FileWriter("Resouces.csv");
            String historyLine = null;
            for(int i = 0; i < manager.getHistory().size(); i++) {
                historyLine = String.valueOf(manager.getHistory().get(i).getId());
                historyLine = "," + historyLine;
                fileWriter.write(historyLine);
            }
            return historyLine;
        } catch (ManagerSaveException e) {
            throw new ManagerSaveException("ERROR HISTORY WAS NOT SAVED");
        }
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

            Path path = Paths.get("Resources.csv");
            FileWriter fileWriter = new FileWriter(path.toFile());
            fileWriter.write("id,type,name,status,description,epic \n");
            for (int i = 1; i <= getIdNewNum(); i++) {
                if(getTaskById().containsKey(i)) {
                    fileWriter.write(taskToString(getTaskById().get(i)) + "\n");
                } else if (getEpicById().containsKey(i)) {
                    fileWriter.write(epicToString(getEpicById().get(i)) + "\n");
                } else if (getSubtaskById().containsKey(i)) {
                    fileWriter.write(subtaskToString(getSubtaskById().get(i)) + "\n");
                }
            }
            fileWriter.write("\n");
            fileWriter.write("History Task");
            fileWriter.write("\n");
            fileWriter.write(FileBackedTasksManager.historyToString(getDefaultHistory()));
            fileWriter.close();

        } catch (ManagerSaveException exception) {
            throw new ManagerSaveException("Error task has not been saved " + exception.getMessage());
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

