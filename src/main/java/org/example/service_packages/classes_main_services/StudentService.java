package org.example.service_packages.classes_main_services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.service_packages.classes.Student;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.example.service_packages.util.AppUtils.intScanner;
import static org.example.service_packages.util.AppUtils.strScanner;

public class StudentService {
    public static void run() throws URISyntaxException, IOException, InterruptedException {
        w:
        while (true) {
            menu();
            switch (intScanner.nextInt()) {
                case 1 -> showAllStudents();
                case 2 -> showStudentById();
                case 3 -> createStudent();
                case 4 -> updateStudent();
                case 5 -> deleteStudent();
                case 6 -> {
                    break w;
                }
                default -> System.out.println("wrong choice");
            }
        }
    }

    private static void deleteStudent() throws URISyntaxException, IOException, InterruptedException {
        System.out.println("choose student to update");
        showAllStudents();
        long studentId = intScanner.nextLong();

        String url = "http://localhost:9090/api/students/" + studentId;

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(
                request, HttpResponse.BodyHandlers.ofString()
        );

        int statusCode = response.statusCode();

        if (statusCode == 204) System.out.println("student deleted");
        else System.out.println("student can't be deleted");

        System.out.println("------------------------------------------");
    }

    private static void updateStudent() throws URISyntaxException, IOException, InterruptedException {
        System.out.println("choose student to update");
        showAllStudents();
        long studentId = intScanner.nextLong();

        Student student = new_updated_Student();

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(student);

        String url = "http://localhost:9090/api/students/" + studentId;

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(
                request, HttpResponse.BodyHandlers.ofString()
        );

        String body = response.body();
        Student student1 = gson.fromJson(body, Student.class);
        System.out.println(student1);
        System.out.println("student updated");
        System.out.println("----------------------------------------");
    }

    private static void createStudent() throws URISyntaxException, IOException, InterruptedException {
        Student student = new_updated_Student();

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(student);

        String url = "http://localhost:9090/api/students";

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(
                request, HttpResponse.BodyHandlers.ofString()
        );

        String body = response.body();
        Student student1 = gson.fromJson(body, Student.class);

        System.out.println(student1);
        System.out.println("student created");
        System.out.println("-----------------------");
    }

    private static void showStudentById() throws URISyntaxException, IOException, InterruptedException {
        System.out.println("enter student id to show");
        long studentId = intScanner.nextLong();

        String url = "http://localhost:9090/api/students/" + studentId;

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request, HttpResponse.BodyHandlers.ofString()
        );

        String body = response.body();

        if (body == null || body.isEmpty()) {
            System.out.println("there is no such student");
        } else {
            Gson gson = new GsonBuilder().create();
            Student student = gson.fromJson(body, Student.class);
            System.out.println(student);
        }
        System.out.println("----------------------------");
    }

    private static void showAllStudents() throws URISyntaxException, IOException, InterruptedException {
        String url = "http://localhost:9090/api/students";

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request, HttpResponse.BodyHandlers.ofString()
        );

        String body = response.body();

        Gson gson = new GsonBuilder().create();

        Student[] students = gson.fromJson(body, Student[].class);

        for (Student student : students) {
            System.out.println(student);
        }
        System.out.println("------------------------------");
    }

    private static void menu() {
        System.out.println("""
                 1 - show all students
                 2 - show student by id
                 3 - create student
                 4 - update student
                 5 - delete student \s
                 6 - back to menu
                \s""");
    }

    private static Student new_updated_Student() {
        System.out.println("enter name");
        String name = strScanner.nextLine();
        System.out.println("enter age");
        int age = intScanner.nextInt();
        System.out.println("enter student GPA");
        double GPA = intScanner.nextDouble();
        System.out.println("enter group id");
        long groupId = intScanner.nextLong();

        return Student.builder().
                name(name)
                .age(age)
                .GPA(GPA)
                .groupId(groupId)
                .build();
    }
}
