package com.example.classsync.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.classsync.model.*;
import com.example.classsync.repository.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

@Configuration
public class DataInitializer {
    
    @Bean
    public CommandLineRunner initializeData(
            UserRepository userRepo,
            StudentRepository studentRepo,
            FacultyRepository facultyRepo,
            CourseRepository courseRepo,
            AttendanceRepository attendanceRepo,
            AcademicRecordRepository academicRecordRepo,
            PasswordEncoder passwordEncoder) {
        
        return args -> {
            // Initialize only if no users exist
            if (userRepo.count() > 0) {
                System.out.println("✅ Data already initialized. Skipping...");
                return;
            }
            
            System.out.println("🔄 Initializing sample data...");
            
            // Create Admin User
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@classsync.edu");
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setRole(User.Role.ADMIN);
            admin.setStatus("ACTIVE");
            userRepo.save(admin);
            
            // Create Faculty Users
            User faculty1 = new User();
            faculty1.setUsername("faculty1");
            faculty1.setPassword(passwordEncoder.encode("faculty123"));
            faculty1.setEmail("Dr.Anderson@university.edu");
            faculty1.setFirstName("Dr. Robert");
            faculty1.setLastName("Anderson");
            faculty1.setRole(User.Role.FACULTY);
            faculty1.setStatus("ACTIVE");
            userRepo.save(faculty1);
            
            User faculty2 = new User();
            faculty2.setUsername("faculty2");
            faculty2.setPassword(passwordEncoder.encode("faculty123"));
            faculty2.setEmail("Dr.Martinez@university.edu");
            faculty2.setFirstName("Dr. Patricia");
            faculty2.setLastName("Martinez");
            faculty2.setRole(User.Role.FACULTY);
            faculty2.setStatus("ACTIVE");
            userRepo.save(faculty2);
            
            // Create Student Users
            User student1 = new User();
            student1.setUsername("student1");
            student1.setPassword(passwordEncoder.encode("student123"));
            student1.setEmail("john.smith@university.edu");
            student1.setFirstName("John");
            student1.setLastName("Smith");
            student1.setRole(User.Role.STUDENT);
            student1.setStatus("ACTIVE");
            userRepo.save(student1);
            
            User student2 = new User();
            student2.setUsername("student2");
            student2.setPassword(passwordEncoder.encode("student123"));
            student2.setEmail("sarah.johnson@university.edu");
            student2.setFirstName("Sarah");
            student2.setLastName("Johnson");
            student2.setRole(User.Role.STUDENT);
            student2.setStatus("ACTIVE");
            userRepo.save(student2);
            
            User student3 = new User();
            student3.setUsername("student3");
            student3.setPassword(passwordEncoder.encode("student123"));
            student3.setEmail("michael.chen@university.edu");
            student3.setFirstName("Michael");
            student3.setLastName("Chen");
            student3.setRole(User.Role.STUDENT);
            student3.setStatus("ACTIVE");
            userRepo.save(student3);
            
            // Create Faculty Records
            Faculty fac1 = new Faculty();
            fac1.setFacultyId("FAC001");
            fac1.setDepartment("Computer Science");
            fac1.setSpecialization("Data Structures");
            fac1.setUser(faculty1);
            facultyRepo.save(fac1);
            
            Faculty fac2 = new Faculty();
            fac2.setFacultyId("FAC002");
            fac2.setDepartment("Mathematics");
            fac2.setSpecialization("Calculus");
            fac2.setUser(faculty2);
            facultyRepo.save(fac2);
            
            // Create Courses
            Course course1 = new Course();
            course1.setCourseCode("CS101");
            course1.setCourseName("Introduction to Computer Science");
            course1.setDescription("Fundamental concepts of computer science");
            course1.setCredits(3);
            course1.setSemester(1);
            course1.setFaculty(fac1);
            courseRepo.save(course1);
            
            Course course2 = new Course();
            course2.setCourseCode("CS201");
            course2.setCourseName("Data Structures");
            course2.setDescription("Advanced data structures and algorithms");
            course2.setCredits(4);
            course2.setSemester(2);
            course2.setFaculty(fac1);
            courseRepo.save(course2);
            
            Course course3 = new Course();
            course3.setCourseCode("MATH101");
            course3.setCourseName("Calculus I");
            course3.setDescription("Differential and Integral Calculus");
            course3.setCredits(4);
            course3.setSemester(1);
            course3.setFaculty(fac2);
            courseRepo.save(course3);
            
            // Create Student Records
            Student std1 = new Student();
            std1.setStudentId("STU001");
            std1.setDepartment("Computer Science");
            std1.setSemester(2);
            std1.setUser(student1);
            std1.setGpa(3.85);
            std1.setCourses(new ArrayList<>(Arrays.asList(course1, course2)));
            studentRepo.save(std1);
            
            Student std2 = new Student();
            std2.setStudentId("STU002");
            std2.setDepartment("Mathematics");
            std2.setSemester(1);
            std2.setUser(student2);
            std2.setGpa(3.72);
            std2.setCourses(new ArrayList<>(Arrays.asList(course1, course3)));
            studentRepo.save(std2);
            
            Student std3 = new Student();
            std3.setStudentId("STU003");
            std3.setDepartment("Computer Science");
            std3.setSemester(1);
            std3.setUser(student3);
            std3.setGpa(3.65);
            std3.setCourses(new ArrayList<>(Arrays.asList(course2, course3)));
            studentRepo.save(std3);
            
            // Create Attendance Records
            Attendance att1 = new Attendance();
            att1.setStudent(std1);
            att1.setCourse(course1);
            att1.setDate(LocalDate.now().minusDays(5));
            att1.setPresent(true);
            attendanceRepo.save(att1);
            
            Attendance att2 = new Attendance();
            att2.setStudent(std1);
            att2.setCourse(course1);
            att2.setDate(LocalDate.now().minusDays(3));
            att2.setPresent(true);
            attendanceRepo.save(att2);
            
            Attendance att3 = new Attendance();
            att3.setStudent(std2);
            att3.setCourse(course3);
            att3.setDate(LocalDate.now().minusDays(2));
            att3.setPresent(false);
            att3.setRemarks("Medical leave");
            attendanceRepo.save(att3);
            
            // Create Academic Records
            AcademicRecord rec1 = new AcademicRecord();
            rec1.setStudent(std1);
            rec1.setCourse(course1);
            rec1.setMidtermMarks(42.0);
            rec1.setFinalMarks(38.0);
            rec1.setProjectMarks(20.0);
            rec1.setTotalMarks(100.0);
            rec1.setGrade("A");
            rec1.setGradePoint(4.0);
            academicRecordRepo.save(rec1);
            
            AcademicRecord rec2 = new AcademicRecord();
            rec2.setStudent(std2);
            rec2.setCourse(course3);
            rec2.setMidtermMarks(40.0);
            rec2.setFinalMarks(35.0);
            rec2.setProjectMarks(18.0);
            rec2.setTotalMarks(93.0);
            rec2.setGrade("A");
            rec2.setGradePoint(4.0);
            academicRecordRepo.save(rec2);
            
            AcademicRecord rec3 = new AcademicRecord();
            rec3.setStudent(std3);
            rec3.setCourse(course2);
            rec3.setMidtermMarks(38.0);
            rec3.setFinalMarks(32.0);
            rec3.setProjectMarks(15.0);
            rec3.setTotalMarks(85.0);
            rec3.setGrade("B+");
            rec3.setGradePoint(3.5);
            academicRecordRepo.save(rec3);
            
            System.out.println("✅ Sample data initialized successfully!");
            System.out.println("📊 Users: 5 (1 Admin, 2 Faculty, 3 Students)");
            System.out.println("📚 Courses: 3");
            System.out.println("📋 Attendance Records: 3");
            System.out.println("📈 Academic Records: 3");
        };
    }
}
