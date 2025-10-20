package org.example.service_packages.classes_main_services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.service_packages.classes.Group;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.example.service_packages.util.AppUtils.intScanner;
import static org.example.service_packages.util.AppUtils.strScanner;

public class GroupService {
    public static void run() throws URISyntaxException, IOException, InterruptedException {
        w:
        while (true) {
            showMenu();
            switch (intScanner.nextInt()) {
                case 1 -> showGroups();
                case 2 -> showSingleGroupById();
                case 3 -> createGroup();
                case 4 -> updateGroup();
                case 5 -> deleteGroup();
                case 6 -> {
                    break w;
                }
                default -> System.out.println("wrong choice");
            }
        }
    }

    private static void deleteGroup() throws URISyntaxException, IOException, InterruptedException {
        System.out.println("enter id");
        long id = intScanner.nextLong();

        String url = "http://localhost:9090/api/groups/" + id;/// http - HTTP_1_1 version(free)

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.version());

        int statusCode = response.statusCode();
        if (statusCode == 204) {
            System.out.println("group is deleted");
        } else {
            System.out.println("smth went wrong");
        }
        System.out.println("------------------------------------------");
    }

    private static void updateGroup() throws URISyntaxException, IOException, InterruptedException {
        System.out.println("enter id");
        long groupId = intScanner.nextLong();
        System.out.println("new name");
        String name = strScanner.nextLine();
        System.out.println("new level");
        int level = intScanner.nextInt();

        Group group = Group.builder()
                .name(name)
                .level(level)
                .build();

        Gson gson = new GsonBuilder().create();
        String gsonBody = gson.toJson(group);

        String url = "http://localhost:9090/api/groups/" + groupId;/// http - HTTP_1_1 version(free)

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .headers("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(gsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();
        Group group1 = gson.fromJson(body, Group.class);
        System.out.println(group1);
        System.out.println("------------------------------");

    }

    private static void createGroup() throws URISyntaxException, IOException, InterruptedException {
        System.out.println("enter new group name");
        String groupName = strScanner.nextLine();
        System.out.println("enter group level");
        int level = intScanner.nextInt();

        Group group = Group.builder()
                .name(groupName)
                .level(level)
                .build();

        Gson toJson = new GsonBuilder()
                .create();
        String jsonBody = toJson.toJson(group);

        String url = "http://localhost:9090/api/groups";/// http - HTTP_1_1 version(free)

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.version());
        String body = response.body();

        Group group1 = toJson.fromJson(body, Group.class);
        System.out.println(group1);
        System.out.println("new group added");
        System.out.println("-------------------------");
    }

    private static void showSingleGroupById() throws URISyntaxException, IOException, InterruptedException {
        System.out.println("enter group id");
        long groupId = intScanner.nextLong();

        String url = "http://localhost:9090/api/groups/" + groupId;/// http - HTTP_1_1 version(free)

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

        if (body == null || body.isEmpty()) {
            System.out.println("wrong id");
        } else {
            Gson gson = new GsonBuilder()
                    .create();

            Group fromJson = gson.fromJson(body, Group.class);
            System.out.println(fromJson);
        }
        System.out.println("------------------------------------------");
    }

    private static void showGroups() throws URISyntaxException, IOException, InterruptedException {
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

        Gson gson = new GsonBuilder()
                .create();

        Group[] fromJson = gson.fromJson(body, Group[].class);

        if (fromJson.length == 0) System.out.println("no groups exist");
        else {
            for (Group group : fromJson) {
                System.out.println(group);
            }
        }

        System.out.println("---------------------------------------");
    }

    private static void showMenu() {
        System.out.println("""
                1 - show all groups
                2 - show group by id
                3 - create new group
                4 - update group
                5 - delete group
                6 - back to menu
                """);
    }
}
