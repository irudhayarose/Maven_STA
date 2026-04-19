package com.example.classsync.repository;

import com.example.classsync.model.AcademicRecord;
import com.example.classsync.model.Student;
import com.example.classsync.model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AcademicRecordRepository extends MongoRepository<AcademicRecord, String> {
    List<AcademicRecord> findByStudent(Student student);
    List<AcademicRecord> findByStudentAndCourse(Student student, Course course);
}
