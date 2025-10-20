package org.example.service_packages.classes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
    private Long id;
    private String name;
    private Integer age;
    private Double GPA;
    private Long groupId;
}
