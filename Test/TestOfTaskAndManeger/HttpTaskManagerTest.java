package TestOfTaskAndManeger;

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
    private URI uriDelete;
    private URI uri;

    @BeforeEach
    public void setUp() throws IOException {
        taskManager = Managers.getDefaultMemory();
        httpTaskManager = new HttpTaskManager("http://localhost:");

        uriDelete = URI.create("http://localhost:8080/tasks/");
        uri = URI.create("http://localhost:8080/tasks/task");
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
    public void shouldNotPostTask() {
        Task task = new Task();
        Gson gson = new Gson();
        String taskJson = gson.toJson(task);
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest requestPost = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .uri(uri)
                .build();
        Assertions.assertThrows(IOException.class , () -> {
            HttpResponse<String> response = httpClient.send(requestPost, HttpResponse.BodyHandlers.ofString());
        });
    }

    @Test
    public void shouldGetTask() throws IOException, InterruptedException {
        Task task = new Task("HELLO" , StatusTask.NEW , "WHATS WRONG" ,
                LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                Duration.ofMinutes(100));
        httpTaskManager.crateTask(task);
        HttpRequest requestDelete = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(requestDelete, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals(1 , httpTaskManager.printAllTask().size());
    }

    @Test
    public void shouldDeleteAllTask() throws IOException, InterruptedException {
        Task task = new Task("HELLO" , StatusTask.NEW , "WHATS WRONG" ,
                LocalDateTime.of(2000, 10 , 18 , 20 , 18) ,
                Duration.ofMinutes(100));
        httpTaskManager.crateTask(task);
        HttpClient httpClient = HttpClient.newHttpClient();

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
        String taskJson = gson.toJson(task);
        URI url = URI.create("http://localhost:8080/tasks/task");
        String json = gson.toJson(task);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        String expected = request.toString();


        assertEquals(expected, "http://localhost:8080/tasks/task POST");
    }
}
