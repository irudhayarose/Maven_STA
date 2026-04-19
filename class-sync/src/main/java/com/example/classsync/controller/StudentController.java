package com.example.classsync.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import com.example.classsync.model.*;
import com.example.classsync.repository.*;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/student")
public class StudentController {

    private final UserRepository userRepo;
    private final StudentRepository studentRepo;
    private final CourseRepository courseRepo;
    private final AttendanceRepository attendanceRepo;
    private final AcademicRecordRepository academicRecordRepo;

    public StudentController(UserRepository userRepo, StudentRepository studentRepo,
                            CourseRepository courseRepo, AttendanceRepository attendanceRepo,
                            AcademicRecordRepository academicRecordRepo) {
        this.userRepo = userRepo;
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
        this.attendanceRepo = attendanceRepo;
        this.academicRecordRepo = academicRecordRepo;
    }

    private Student getCurrentStudent(Authentication auth) {
        User user = userRepo.findByUsername(auth.getName()).orElse(null);
        if (user == null) return null;
        return studentRepo.findAll().stream()
            .filter(s -> s.getUser().getId().equals(user.getId()))
            .findFirst().orElse(null);
    }

    @GetMapping("/dashboard")
    public String studentDashboard(Model model, Authentication auth) {
        Student student = getCurrentStudent(auth);
        if (student != null) {
            model.addAttribute("student", student);
            model.addAttribute("courses", student.getCourses());
            List<AcademicRecord> records = academicRecordRepo.findByStudent(student);
            model.addAttribute("records", records);
            List<Attendance> attendances = attendanceRepo.findAll().stream()
                .filter(a -> a.getStudent().getId().equals(student.getId()))
                .collect(Collectors.toList());
            model.addAttribute("attendances", attendances);
        }
        return "student/dashboard";
    }

    @GetMapping("/attendance")
    public String attendance(Model model, Authentication auth) {
        Student student = getCurrentStudent(auth);
        if (student != null) {
            model.addAttribute("student", student);
            List<Attendance> attendances = attendanceRepo.findAll().stream()
                .filter(a -> a.getStudent().getId().equals(student.getId()))
                .collect(Collectors.toList());
            model.addAttribute("attendances", attendances);
            // Calculate summary
            long total = attendances.size();
            long present = attendances.stream().filter(Attendance::getPresent).count();
            model.addAttribute("totalClasses", total);
            model.addAttribute("presentCount", present);
            model.addAttribute("percentage", total > 0 ? Math.round((present * 100.0) / total) : 0);
        }
        return "student/attendance";
    }

    @GetMapping("/marks")
    public String marks(Model model, Authentication auth) {
        Student student = getCurrentStudent(auth);
        if (student != null) {
            model.addAttribute("student", student);
            List<AcademicRecord> records = academicRecordRepo.findByStudent(student);
            model.addAttribute("records", records);
            // Calculate GPA
            double totalGradePoints = records.stream().mapToDouble(AcademicRecord::getGradePoint).sum();
            double gpa = records.isEmpty() ? 0 : totalGradePoints / records.size();
            model.addAttribute("gpa", String.format("%.2f", gpa));
        }
        return "student/marks";
    }
}
