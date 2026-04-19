package com.example.classsync.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "academic_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcademicRecord {
    @Id
    private String id;
    
    @DBRef
    private Student student;
    
    @DBRef
    private Course course;
    
    private Double midtermMarks;
    private Double finalMarks;
    private Double projectMarks;
    private Double totalMarks;
    private String grade;
    private Double gradePoint;
}
