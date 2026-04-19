package com.example.classsync.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.index.Indexed;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.ArrayList;

@Document(collection = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String studentId;
    
    private String department;
    
    private Integer semester;
    
    @DBRef
    private User user;
    
    @DBRef
    private List<Course> courses = new ArrayList<>();
    
    private Double gpa = 0.0;
}
