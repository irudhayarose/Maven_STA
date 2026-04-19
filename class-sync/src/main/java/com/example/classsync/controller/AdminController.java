package com.example.classsync.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.classsync.model.*;
import com.example.classsync.repository.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepo;
    private final StudentRepository studentRepo;
    private final FacultyRepository facultyRepo;
    private final CourseRepository courseRepo;
    private final AttendanceRepository attendanceRepo;
    private final AcademicRecordRepository academicRecordRepo;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserRepository userRepo, StudentRepository studentRepo,
                          FacultyRepository facultyRepo, CourseRepository courseRepo,
                          AttendanceRepository attendanceRepo, AcademicRecordRepository academicRecordRepo,
                          PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.studentRepo = studentRepo;
        this.facultyRepo = facultyRepo;
        this.courseRepo = courseRepo;
        this.attendanceRepo = attendanceRepo;
        this.academicRecordRepo = academicRecordRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // --- Users ---
    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userRepo.findAll());
        return "admin/users";
    }

    @GetMapping("/users/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/user-form";
    }

    @PostMapping("/users/add")
    public String addUser(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getStatus() == null || user.getStatus().isEmpty()) {
            user.setStatus("ACTIVE");
        }
        userRepo.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable String id) {
        userRepo.deleteById(id);
        return "redirect:/admin/users";
    }

    // --- Students ---
    @GetMapping("/students")
    public String listStudents(Model model) {
        model.addAttribute("students", studentRepo.findAll());
        return "admin/students";
    }

    @GetMapping("/students/add")
    public String addStudentForm(Model model) {
        model.addAttribute("student", new Student());
        List<User> studentUsers = userRepo.findAll().stream()
            .filter(u -> u.getRole() == User.Role.STUDENT)
            .toList();
        model.addAttribute("users", studentUsers);
        model.addAttribute("courses", courseRepo.findAll());
        return "admin/student-form";
    }

    @PostMapping("/students/add")
    public String addStudent(@RequestParam String studentId,
                            @RequestParam String department,
                            @RequestParam Integer semester,
                            @RequestParam String userId,
                            @RequestParam(required = false) List<String> courseIds) {
        Student student = new Student();
        student.setStudentId(studentId);
        student.setDepartment(department);
        student.setSemester(semester);
        student.setUser(userRepo.findById(userId).orElse(null));
        student.setGpa(0.0);
        if (courseIds != null) {
            List<Course> courses = new ArrayList<>();
            for (String cid : courseIds) {
                courseRepo.findById(cid).ifPresent(courses::add);
            }
            student.setCourses(courses);
        }
        studentRepo.save(student);
        return "redirect:/admin/students";
    }

    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable String id) {
        studentRepo.deleteById(id);
        return "redirect:/admin/students";
    }

    // --- Courses ---
    @GetMapping("/courses")
    public String listCourses(Model model) {
        model.addAttribute("courses", courseRepo.findAll());
        return "admin/courses";
    }

    @GetMapping("/courses/add")
    public String addCourseForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("facultyList", facultyRepo.findAll());
        return "admin/course-form";
    }

    @PostMapping("/courses/add")
    public String addCourse(@RequestParam String courseCode,
                           @RequestParam String courseName,
                           @RequestParam(required = false) String description,
                           @RequestParam Integer credits,
                           @RequestParam Integer semester,
                           @RequestParam String facultyId) {
        Course course = new Course();
        course.setCourseCode(courseCode);
        course.setCourseName(courseName);
        course.setDescription(description);
        course.setCredits(credits);
        course.setSemester(semester);
        course.setFaculty(facultyRepo.findById(facultyId).orElse(null));
        courseRepo.save(course);
        return "redirect:/admin/courses";
    }

    @GetMapping("/courses/delete/{id}")
    public String deleteCourse(@PathVariable String id) {
        courseRepo.deleteById(id);
        return "redirect:/admin/courses";
    }

    // --- Faculty ---
    @GetMapping("/faculty")
    public String listFaculty(Model model) {
        model.addAttribute("facultyList", facultyRepo.findAll());
        return "admin/faculty";
    }
}
