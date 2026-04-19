package com.example.classsync.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.index.Indexed;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String courseCode;
    
    private String courseName;
    
    private String description;
    
    private Integer credits;
    
    private Integer semester;
    
    @DBRef
    private Faculty faculty;
}
