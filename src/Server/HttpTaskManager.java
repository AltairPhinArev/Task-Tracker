package Server;

import Manager.*;
import Task.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpTaskManager extends FileBackedTasksManager {

    private KVServerClient kvServerClient;
    HttpTaskServer httpTaskServer;

    public HttpTaskManager(String url) {
        try {
            httpTaskServer = new HttpTaskServer();;
            httpTaskServer.start();
            //  kvServerClient = new KVServerClient(url , 8080);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void removeAllTask() {
        super.removeAllTask();
    }

    @Override
    public void crateTask(Task task) throws IOException {
        super.crateTask(task);
        //kvServerClient.save("TASK" , FileBackedTasksManager.taskById.get(task.getId()).toString());
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
        kvServerClient.save("SUBTASK" , FileBackedTasksManager.subtaskById.get(subtask.getId()).toString());
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
    }

    @Override
    public void createEpic(Epic epic) throws IOException {
        super.createEpic(epic);
        kvServerClient.save("EPIC" ,  FileBackedTasksManager.epicById.get(epic.getId()).toString());
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
    }
}