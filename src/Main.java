import Manager.*;
import Server.HttpTaskManager;
import Server.KVServerClient;


import java.io.File;
import java.io.IOException;



public class Main {
    public static void main(String[] args) throws IOException {
        HttpTaskManager httpTaskManager = new HttpTaskManager("http://localhost:");
        FileBackedTasksManager taskManager = new FileBackedTasksManager(new File("Resources.csv"));
        FileBackedTasksManager.loadFromFile(new File("Resources.csv"));
        KVServerClient kvServerClient = new KVServerClient("http://localhost:" , 8080);


    }
}