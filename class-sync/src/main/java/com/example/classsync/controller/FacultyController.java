package com.example.classsync.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import com.example.classsync.model.*;
import com.example.classsync.repository.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/faculty")
public class FacultyController {

    private final UserRepository userRepo;
    private final StudentRepository studentRepo;
    private final FacultyRepository facultyRepo;
    private final CourseRepository courseRepo;
    private final AttendanceRepository attendanceRepo;
    private final AcademicRecordRepository academicRecordRepo;

    public FacultyController(UserRepository userRepo, StudentRepository studentRepo,
                            FacultyRepository facultyRepo, CourseRepository courseRepo,
                            AttendanceRepository attendanceRepo, AcademicRecordRepository academicRecordRepo) {
        this.userRepo = userRepo;
        this.studentRepo = studentRepo;
        this.facultyRepo = facultyRepo;
        this.courseRepo = courseRepo;
        this.attendanceRepo = attendanceRepo;
        this.academicRecordRepo = academicRecordRepo;
    }

    private Faculty getCurrentFaculty(Authentication auth) {
        User user = userRepo.findByUsername(auth.getName()).orElse(null);
        if (user == null) return null;
        return facultyRepo.findAll().stream()
            .filter(f -> f.getUser().getId().equals(user.getId()))
            .findFirst().orElse(null);
    }

    @GetMapping("/students")
    public String myStudents(Model model, Authentication auth) {
        Faculty faculty = getCurrentFaculty(auth);
        if (faculty != null) {
            List<Course> myCourses = courseRepo.findAll().stream()
                .filter(c -> c.getFaculty() != null && c.getFaculty().getId().equals(faculty.getId()))
                .collect(Collectors.toList());
            List<Student> students = studentRepo.findAll().stream()
                .filter(s -> s.getCourses() != null && s.getCourses().stream()
                    .anyMatch(sc -> myCourses.stream().anyMatch(mc -> mc.getId().equals(sc.getId()))))
                .collect(Collectors.toList());
            model.addAttribute("students", students);
            model.addAttribute("courses", myCourses);
        }
        return "faculty/students";
    }

    @GetMapping("/attendance")
    public String attendancePage(Model model, Authentication auth) {
        Faculty faculty = getCurrentFaculty(auth);
        if (faculty != null) {
            List<Course> myCourses = courseRepo.findAll().stream()
                .filter(c -> c.getFaculty() != null && c.getFaculty().getId().equals(faculty.getId()))
                .collect(Collectors.toList());
            model.addAttribute("courses", myCourses);
            model.addAttribute("students", studentRepo.findAll());
            model.addAttribute("today", LocalDate.now());
        }
        return "faculty/attendance";
    }

    @PostMapping("/attendance/mark")
    public String markAttendance(@RequestParam String courseId,
                                @RequestParam String studentId,
                                @RequestParam String date,
                                @RequestParam Boolean present,
                                @RequestParam(required = false) String remarks) {
        Attendance att = new Attendance();
        att.setStudent(studentRepo.findById(studentId).orElse(null));
        att.setCourse(courseRepo.findById(courseId).orElse(null));
        att.setDate(LocalDate.parse(date));
        att.setPresent(present);
        att.setRemarks(remarks);
        attendanceRepo.save(att);
        return "redirect:/faculty/attendance?success";
    }

    @GetMapping("/marks")
    public String marksPage(Model model, Authentication auth) {
        Faculty faculty = getCurrentFaculty(auth);
        if (faculty != null) {
            List<Course> myCourses = courseRepo.findAll().stream()
                .filter(c -> c.getFaculty() != null && c.getFaculty().getId().equals(faculty.getId()))
                .collect(Collectors.toList());
            model.addAttribute("courses", myCourses);
            model.addAttribute("students", studentRepo.findAll());
            model.addAttribute("records", academicRecordRepo.findAll());
        }
        return "faculty/marks";
    }

    @PostMapping("/marks/upload")
    public String uploadMarks(@RequestParam String courseId,
                             @RequestParam String studentId,
                             @RequestParam Double midtermMarks,
                             @RequestParam Double finalMarks,
                             @RequestParam Double projectMarks) {
        AcademicRecord record = new AcademicRecord();
        record.setStudent(studentRepo.findById(studentId).orElse(null));
        record.setCourse(courseRepo.findById(courseId).orElse(null));
        record.setMidtermMarks(midtermMarks);
        record.setFinalMarks(finalMarks);
        record.setProjectMarks(projectMarks);
        double total = midtermMarks + finalMarks + projectMarks;
        record.setTotalMarks(total);

        // Auto-calculate grade
        if (total >= 90) { record.setGrade("A+"); record.setGradePoint(4.0); }
        else if (total >= 85) { record.setGrade("A"); record.setGradePoint(4.0); }
        else if (total >= 80) { record.setGrade("A-"); record.setGradePoint(3.7); }
        else if (total >= 75) { record.setGrade("B+"); record.setGradePoint(3.3); }
        else if (total >= 70) { record.setGrade("B"); record.setGradePoint(3.0); }
        else if (total >= 65) { record.setGrade("B-"); record.setGradePoint(2.7); }
        else if (total >= 60) { record.setGrade("C+"); record.setGradePoint(2.3); }
        else if (total >= 55) { record.setGrade("C"); record.setGradePoint(2.0); }
        else if (total >= 50) { record.setGrade("D"); record.setGradePoint(1.0); }
        else { record.setGrade("F"); record.setGradePoint(0.0); }

        academicRecordRepo.save(record);
        return "redirect:/faculty/marks?success";
    }
}
