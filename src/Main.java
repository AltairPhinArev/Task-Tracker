import Manager.*;
import Server.HttpTaskManager;
import Server.KVServerClient;
import Task.Task;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;


public class Main {
    public static void main(String[] args) throws IOException {
        HttpTaskManager httpTaskManager = new HttpTaskManager("http://localhost:");
        FileBackedTasksManager taskManager = new FileBackedTasksManager(new File("Resources.csv"));
        FileBackedTasksManager.loadFromFile(new File("Resources.csv"));
        KVServerClient kvServerClient = new KVServerClient("http://localhost:" , 8080);

         Task task =  new Task("HELLO"  , "WHATS WRONG" ,
                 LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                 Duration.ofMinutes(100));

        Gson gson = new Gson();
        kvServerClient.save("KEY" ,gson.toJson(taskManager.printTaskById(1)));

    }
}