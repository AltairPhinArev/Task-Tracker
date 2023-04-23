package Server;

import Manager.ManagerSaveException;
import org.junit.platform.commons.util.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class KVServerClient {
    private final String apiToken;
    private final String url;
    private final int port;


    public KVServerClient(String url, int port) throws ManagerSaveException {
        this.port = port;
        this.url = url;
        URI register = URI.create(url + port + "/register");
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(register)
                .header("TYPE", "application/json")
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response;
        try {
            response = client.send(request, handler);
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ManagerSaveException("ERROR REGISTER" + e.getMessage());
        }
        apiToken = response.body();
    }

    public void save(String key, String gson) throws ManagerSaveException {
        URI save = URI.create(url + port + "/save/" + key + "?API_TOKEN=" + apiToken);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(gson))
                .uri(save)
                .header("TYPE", "application/json")
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response;
        try {
            response = client.send(request, handler);
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ManagerSaveException("Error save to server" + e.getMessage());
        }
        if (StringUtils.isBlank(String.valueOf(response)) || response.statusCode() != 200) {
            throw new ManagerSaveException("Error save to server");
        }
    }

    public String load(String key) throws ManagerSaveException {
        URI load = URI.create(url + port + "/load/" + key + "?API_TOKEN=" + apiToken);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(load)
                .header("TYPE", "application/json")
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response;
        try {
            response = client.send(request, handler);
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ManagerSaveException("Error load from server" + e.getMessage());
        }
        if (StringUtils.isBlank(response.body())) {
            throw new ManagerSaveException("Error load from server");
        }
        return response.body();
    }
}