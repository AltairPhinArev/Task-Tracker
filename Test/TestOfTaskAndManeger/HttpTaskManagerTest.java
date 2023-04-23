package TestOfTaskAndManeger;

import Manager.FileBackedTasksManager;
import Manager.ManagerSaveException;
import Manager.Managers;
import Manager.TaskManager;
import Server.HttpTaskManager;
import Server.KVServerClient;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Task.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


class HttpTaskManagerTest {

    private HttpTaskManager httpTaskManager;
    private KVServerClient  kvServerClient;
    private TaskManager taskManager;
    private Gson gson;
    private String Key = "Key";


    @BeforeEach
    public void setUp() throws IOException {
        gson = new Gson();
        taskManager = Managers.getDefaultMemory();
        httpTaskManager = new HttpTaskManager("http://localhost:");
    }

    @AfterEach
    void tearDown() {
        taskManager = Managers.getDefaultMemory();
    }

    @Test
    void testSave() throws IOException, InterruptedException {
        Task task = new Task("HELLO" , StatusTask.NEW , "WHATS WRONG" ,
                LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                Duration.ofMinutes(100));
            httpTaskManager.crateTask(task);
            assertNotNull(httpTaskManager.printTaskById(task.getId()));
            assertEquals(task , httpTaskManager.printTaskById(task.getId()));
    }

    @Test
    void shouldNotSaveToServer() throws IOException {
        Task task = new Task();
        Assertions.assertThrows(NullPointerException.class , () -> {
            httpTaskManager.crateTask(task);
        });
    }

    @Test
    void shouldLoadToServer() throws ManagerSaveException {
        Task task = new Task();
        Assertions.assertThrows(NullPointerException.class , () -> {
            kvServerClient.load(task.toString());
        });
    }

    @Test
    public void shouldDeleteTaskById() throws IOException, InterruptedException {
        Task task = new Task("HELLO" , StatusTask.NEW , "WHATS WRONG" ,
                LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                Duration.ofMinutes(100));


        HttpClient httpClient = HttpClient.newHttpClient();

        String id = String.valueOf(task.getId());

        URI uriDelete = URI.create("http://localhost:8080/tasks/task/?id=" + id);

        HttpRequest requestDelete = HttpRequest.newBuilder()
                .uri(uriDelete)
                .DELETE()
                .build();
        HttpResponse<String> response = httpClient.send(requestDelete, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
    }

    @Test
    public void shouldPostTask() throws IOException, InterruptedException {
        Task task = new Task("HELLO", StatusTask.NEW, "WHATS WRONG",
                LocalDateTime.of(2000, 10, 18, 20, 18),
                Duration.ofMinutes(100));

        Gson gson = new Gson();
        String taskJson = gson.toJson(task.toString());

        HttpClient httpClient = HttpClient.newHttpClient();
        URI uriPost = URI.create("http://localhost:8080/tasks/task/");
        HttpRequest requestPost = HttpRequest.newBuilder()
                .uri(uriPost)
                .POST(HttpRequest.BodyPublishers.ofString("http://localhost:8080/tasks/task/"))
                .build();
        HttpResponse<String> response = httpClient.send(requestPost, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
    }

    @Test
    public void shouldGetTask() throws IOException, InterruptedException {
        Task task = new Task("HELLO" , StatusTask.NEW , "WHATS WRONG" ,
                LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                Duration.ofMinutes(100));
        httpTaskManager.crateTask(task);
        URI uriGet = URI.create("http://localhost:8080/tasks/");

        HttpRequest requestDelete = HttpRequest.newBuilder()
                .uri(uriGet)
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(requestDelete, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertEquals(0 , httpTaskManager.printAllTask().size());
    }

    @Test
    public void shouldDeleteAllTask() throws IOException, InterruptedException {
        Task task = new Task("HELLO" , StatusTask.NEW , "WHATS WRONG" ,
                LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                Duration.ofMinutes(100));
        httpTaskManager.crateTask(task);

        HttpClient httpClient = HttpClient.newHttpClient();


        URI uriDelete = URI.create("http://localhost:8080/tasks/");

        HttpRequest requestDelete = HttpRequest.newBuilder()
                .uri(uriDelete)
                .DELETE()
                .build();
        HttpResponse<String> response = httpClient.send(requestDelete, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertEquals(0 , httpTaskManager.printAllTask().size());
    }
}
