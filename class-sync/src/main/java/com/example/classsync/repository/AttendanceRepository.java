package com.example.classsync.repository;

import com.example.classsync.model.Attendance;
import com.example.classsync.model.Student;
import com.example.classsync.model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends MongoRepository<Attendance, String> {
    List<Attendance> findByStudentAndCourse(Student student, Course course);
    List<Attendance> findByStudentAndCourseAndDateBetween(Student student, Course course, LocalDate startDate, LocalDate endDate);
}
