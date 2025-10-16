package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientExample {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        /// String url = "https://jsonplaceholder.typicode.com/posts";/// https - HTTP_2 version(cost money)
        String url = "http://localhost:9090/api/groups";/// http - HTTP_1_1 version(free)

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.version());
        String body = response.body();
        System.out.println(body);
    }
}
