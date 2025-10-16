package org.example.service_packages.util;

import org.example.service_packages.GroupService;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.example.service_packages.util.AppUtils.intScanner;

public class Run {
    public static void run() throws URISyntaxException, IOException, InterruptedException {
        s:
        while (true) {
            showMenu();
            switch (intScanner.nextInt()) {
                case 1 -> {
                    GroupService.run();
                }
                case 2 -> {
                    //todo----- StudentService.run();
                }
                case 3 -> {
                    System.out.println("Good bye");
                    break s;
                }
                default -> System.out.println("wrong choice");
            }
        }
    }

    private static void showMenu() {
        System.out.println("""
                1 - group service
                2 - student service 
                3 - exit
                """);
    }
}
