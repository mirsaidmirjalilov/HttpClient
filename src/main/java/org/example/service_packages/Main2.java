package org.example.service_packages;

import org.example.service_packages.classes_main_services.MainService;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main2 {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        MainService.run();
    }
}
