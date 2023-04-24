package Server;

import Manager.FileBackedTasksManager;
import Manager.HistoryManager;
import Manager.Managers;
import Manager.TaskManager;
import Task.*;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import javax.naming.TimeLimitExceededException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.UTF_8;
import static jdk.internal.util.xml.XMLStreamWriter.DEFAULT_CHARSET;

public class HttpTaskServer {
        InputStream inputStream;
        private final int PORT = 8080;
        private final TaskManager taskManager;
        private final String apiToken;
        private final HttpServer server;
         private final Map<String, String> data = new HashMap<>();

    public HttpTaskServer() throws IOException {
        taskManager = new FileBackedTasksManager(new File("Recuses.csv"));
        apiToken = generateApiToken();
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/register", this::register);
        server.createContext("/save", this::save);
        server.createContext("/load", this::load);
        server.createContext("/tasks", this::handler);
    }

    public void handler(HttpExchange exchange) {
        try {
            String path = exchange.getRequestURI().getPath();
            String requestMethod = exchange.getRequestMethod();
            switch (requestMethod) {
                case "GET":
                    if (Pattern.matches("^/tasks/task/\\d+$", path)) {
                        getTaskById(exchange);
                    } else if (Pattern.matches("^/tasks/epic/\\d+$", path)) {
                        getEpicById(exchange);
                    } else if (Pattern.matches("^/tasks/subtask/", path)) {
                        getSubtaskById(exchange);
                    } else if (Pattern.matches("^/tasks/task$", path)) {
                        getAllTask(exchange);
                    } else if (Pattern.matches("^/tasks/epic$", path)) {
                        getAllEpic(exchange);
                    } else if (Pattern.matches("^/tasks/subtask$", path)) {
                        getAllSubtask(exchange);
                    } else if (Pattern.matches("^/tasks/history$", path)) {
                        getHistory(exchange);
                    } else if (Pattern.matches("^/tasks/epic/subtask/\\d+$", path)) {
                        getSubtasksByEpic(exchange);
                    } else if (Pattern.matches("^/tasks/prioritized/", path)){
                        getPrioritizedTasks(exchange);
                    }
                    break;
                case "POST":
                    if (Pattern.matches("^/tasks/task/", path)) {
                        updateTask(exchange);
                    } else if (Pattern.matches("^/tasks/subtask/", path)) {
                        updateSubtask(exchange);
                    } else if (Pattern.matches("^/tasks/epic$", path)) {
                        postEpic(exchange);
                    } else if (Pattern.matches("^/tasks/task$", path)) {
                        postTask(exchange);
                    } else if (Pattern.matches("^/tasks/subtask$", path)) {
                        postSubtask(exchange);
                    }
                    break;
                case "DELETE":
                    if (Pattern.matches("^/tasks/task/\\d+$", path)) {
                        deleteTaskById(exchange);
                    } else if (Pattern.matches("^/tasks/epic/\\d+$", path)) {
                        deleteEpicById(exchange);
                    } else if (Pattern.matches("^/tasks/subtask/\\d+$", path)) {
                        deleteSubtaskById(exchange);
                    } else {
                        deleteTaskAllFromServer(exchange);
                    }
                    break;
                default:
                    System.out.println("Response uncorrected");
                    exchange.sendResponseHeaders(405, 0);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            exchange.close();
        }
    }


    private void updateTask(HttpExchange exchange) throws IOException {
        inputStream  = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes() , DEFAULT_CHARSET);
        Gson gson = new Gson();
        Task task = gson.fromJson(body , Task.class);
        taskManager.updateTask(task);
    }

    private void updateSubtask(HttpExchange exchange) throws IOException {
        inputStream  = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes() , DEFAULT_CHARSET);
        Gson gson = new Gson();
        Subtask subtask = gson.fromJson(body , Subtask.class);
        taskManager.updateSubtaskTitle(subtask);
    }
    private void postTask(HttpExchange exchange) throws IOException, TimeLimitExceededException {
        inputStream  = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes() , DEFAULT_CHARSET);
        Gson gson = new Gson();
        Task task = gson.fromJson(body , Task.class);
        taskManager.crateTask(task);
    }

    private void postEpic(HttpExchange exchange) throws IOException , TimeLimitExceededException {
        inputStream  = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes() , DEFAULT_CHARSET);
        Gson gson = new Gson();
        Epic epic = gson.fromJson(body , Epic.class);
        taskManager.crateTask(epic);
    }

    private void postSubtask(HttpExchange exchange) throws IOException , TimeLimitExceededException {
        inputStream  = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes() , DEFAULT_CHARSET);
        Gson gson = new Gson();
        Subtask subtask = gson.fromJson(body , Subtask.class);
        taskManager.crateTask(subtask);
    }

    private void getEpicById(HttpExchange exchange) throws IOException {
        String[] query = exchange.getRequestURI().getQuery().split("=");
        String[] id = query[1].split(",");
        Gson gson = new Gson();

        ArrayList<Epic> epics = new ArrayList<>();
        for (String param : id) {
            int taskId = Integer.parseInt(param);
            epics.add(taskManager.printEpicById(taskId));
        }

        writeResponse(exchange, gson.toJson(epics), 200);
    }

    private void getAllTask(HttpExchange exchange) throws IOException {;
        Gson gson = new Gson();
        String json = gson.toJson(taskManager.printAllTask());
        writeResponse(exchange , json , 200);
    }

    private void getAllEpic(HttpExchange exchange) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(taskManager.printAllEpic());
        writeResponse(exchange , json , 200);
    }
    private void getAllSubtask(HttpExchange exchange) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(taskManager.printAllSubtask());
        writeResponse(exchange , json , 200);
    }

    private void getTaskById(HttpExchange exchange) throws IOException{
        String[] query = exchange.getRequestURI().getQuery().split("=");
        String[] id = query[1].split(",");
        Gson gson = new Gson();

        ArrayList<Task> tasks = new ArrayList<>();
        for (String param : id) {
            int taskId = Integer.parseInt(param);
            tasks.add(taskManager.printEpicById(taskId));
        }
        writeResponse(exchange, gson.toJson(tasks), 200);
    }


    private void getSubtaskById(HttpExchange exchange) throws IOException{
        String[] query = exchange.getRequestURI().getQuery().split("=");
        String[] id = query[1].split(",");
        Gson gson = new Gson();

        ArrayList<Subtask> subtasks = new ArrayList<>();
        for (String param : id) {
            int taskId = Integer.parseInt(param);
            subtasks.add(taskManager.printSubtaskById(taskId));
        }
        writeResponse(exchange, gson.toJson(subtasks), 200);
    }

    private void getSubtasksByEpic(HttpExchange exchange) throws IOException {
        String[] query = exchange.getRequestURI().getQuery().split("=");
        int epicId = Integer.parseInt(query[1]);
        Gson gson = new Gson();
        Epic epic = taskManager.printEpicById(epicId);
        List<Subtask> subtasks = taskManager.printAllSubtaskByEpic(epic);
        writeResponse(exchange, gson.toJson(subtasks), 200);
    }

    private void deleteTaskAllFromServer(HttpExchange exchange) throws IOException, InterruptedException {
        taskManager.removeAllTask();
        writeResponse(exchange , "DELETED" , 200);
    }

    private void deleteEpicById(HttpExchange exchange) throws IOException {
        String[] query = exchange.getRequestURI().getQuery().split("=");
        String[] id = query[1].split(",");

        for (String param : id) {
            int intId = Integer.parseInt(param);
            taskManager.removeEpicById(intId);
        }
        writeResponse(exchange ,"DELETED" , 200);
    }

    public void deleteSubtaskById(HttpExchange exchange) throws IOException {
        String[] query = exchange.getRequestURI().getQuery().split("=");
        String[] id = query[1].split(",");

        for (String param : id) {
            int intId = Integer.parseInt(param);
            taskManager.removeEpicById(intId);
        }
        writeResponse(exchange ,"DELETED" , 200);
    }

    public void deleteTaskById(HttpExchange exchange) throws IOException {
        String[] query = exchange.getRequestURI().getQuery().split("=");
        String[] id = query[1].split(",");

        for (String param : id) {
            int intId = Integer.parseInt(param);
            taskManager.removeTaskById(intId);
        }
        writeResponse(exchange ,"DELETED" , 200);
    }

    public void getHistory(HttpExchange exchange) throws IOException {
        HistoryManager historyManager = Managers.getDefaultHistory();
        List<Task> history = historyManager.getHistory();
        Gson gson = new Gson();
        String jsonHistory = gson.toJson(history);
        writeResponse(exchange , jsonHistory , 200);
    }

    public void getPrioritizedTasks(HttpExchange exchange) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(taskManager.getPrioritizedTasks());
        writeResponse(exchange , json , 200 );
    }

    private void writeResponse(HttpExchange exchange, String responseString, int responseCode) throws IOException {
        if(responseString.isBlank()) {
            exchange.sendResponseHeaders(responseCode, 0);
        } else {
            byte[] bytes = responseString.getBytes(DEFAULT_CHARSET);
            exchange.sendResponseHeaders(responseCode, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
        exchange.close();
    }



        private void load(HttpExchange h) throws IOException {
            try {
                if (!hasAuth(h)) {
                    System.out.println("Запрос неавторизован, нужен параметр ");
                    h.sendResponseHeaders(403 , 0);
                    return;
                }
                if ("GET".equals(h.getRequestMethod())) {
                    String key = h.getRequestURI().getPath().substring(("LOAD" +"/").length());
                    if (key.isEmpty()) {
                        System.out.println("Key для сохранения пустой. key указывается в пути: /" + "SAVE" + "{key}");
                        h.sendResponseHeaders(400, 0);
                        return;
                    }
                    if (!data.containsKey(key)) {
                        System.out.println("Не могу достать данные для ключа '" + key + "', данные отсутствуют");
                        h.sendResponseHeaders(404 , 0);
                        return;
                    }
                    sendText(h, data.get(key));
                    System.out.println("Значение для ключа " + key + " успешно получено!");
                    h.sendResponseHeaders(200, 0);
                } else {
                    System.out.println("LOAD" + " ждёт " + "GET" + "-запрос, а получил: " + h.getRequestMethod());
                    h.sendResponseHeaders(405 , 0);
                }
            } finally {
                h.close();
            }
        }

        public void stop() {
            server.stop(0);
            System.out.println("Сервер на порту " + PORT + " остановлен");
        }

        private void save(HttpExchange h) throws IOException {
            try {
                System.out.println("\n/save");
                if (!hasAuth(h)) {
                    System.out.println("Запрос неавторизован, нужен параметр в query API_TOKEN со значением апи-ключа");
                    h.sendResponseHeaders(403, 0);
                    return;
                }
                if ("POST".equals(h.getRequestMethod())) {
                    String key = h.getRequestURI().getPath().substring("/save/".length());
                    if (key.isEmpty()) {
                        System.out.println("Key для сохранения пустой. key указывается в пути: /save/{key}");
                        h.sendResponseHeaders(400, 0);
                        return;
                    }
                    String value = readText(h);
                    if (value.isEmpty()) {
                        System.out.println("Value для сохранения пустой. value указывается в теле запроса");
                        h.sendResponseHeaders(400, 0);
                        return;
                    }
                    data.put(key, value);
                    System.out.println("Значение для ключа " + key + " успешно обновлено!");
                    h.sendResponseHeaders(200, 0);
                } else {
                    System.out.println("/save ждёт POST-запрос, а получил: " + h.getRequestMethod());
                    h.sendResponseHeaders(405, 0);
                }
            } finally {
                h.close();
            }
        }

        private void register(HttpExchange h) throws IOException {
            try {
                System.out.println("\n/register");
                if ("GET".equals(h.getRequestMethod())) {
                    sendText(h, apiToken);
                } else {
                    System.out.println("/register ждёт GET-запрос, а получил " + h.getRequestMethod());
                    h.sendResponseHeaders(405, 0);
                }
            } finally {
                h.close();
            }
        }

        public void start() {
            System.out.println("Запускаем сервер на порту " + PORT);
            System.out.println("Открой в браузере http://localhost:" + PORT + "/");
            System.out.println("API_TOKEN: " + apiToken);
            server.start();
        }

        private String generateApiToken() {
            return "" + System.currentTimeMillis();
        }

        protected boolean hasAuth(HttpExchange h) {
            String rawQuery = h.getRequestURI().getRawQuery();
            return rawQuery != null && (rawQuery.contains("API_TOKEN=" + apiToken) || rawQuery.contains("API_TOKEN=DEBUG"));
        }

        protected String readText(HttpExchange h) throws IOException {
            return new String(h.getRequestBody().readAllBytes(), UTF_8);
        }

        protected void sendText(HttpExchange h, String text) throws IOException {
            byte[] resp = text.getBytes(UTF_8);
            h.getResponseHeaders().add("Content-Type", "application/json");
            h.sendResponseHeaders(200, resp.length);
            h.getResponseBody().write(resp);
        }
    }

