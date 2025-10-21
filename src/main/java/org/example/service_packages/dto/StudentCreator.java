package org.example.service_packages.dto;

public record StudentCreator(
        String name,
        Integer age,
        Double gpa,
        Long groupId
) {
}
