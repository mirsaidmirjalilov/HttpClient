package org.example.service_packages.classeServices;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.example.service_packages.util.AppUtils.intScanner;

public class MainRun {
    public static void run() throws URISyntaxException, IOException, InterruptedException {
        s:
        while (true) {
            showMenu();
            switch (intScanner.nextInt()) {
                case 1 -> {
                    GroupService.run();
                }
                case 2 -> {
                    StudentService.run();
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
                 2 - student service\s
                 3 - exit
                \s""");
    }
}
