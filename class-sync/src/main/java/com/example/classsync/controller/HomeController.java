package com.example.classsync.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import com.example.classsync.repository.*;
import org.springframework.security.core.Authentication;

@Controller
public class HomeController {
    
    private final UserRepository userRepo;
    private final StudentRepository studentRepo;
    private final FacultyRepository facultyRepo;
    private final CourseRepository courseRepo;
    
    public HomeController(UserRepository userRepo, StudentRepository studentRepo, 
                         FacultyRepository facultyRepo, CourseRepository courseRepo) {
        this.userRepo = userRepo;
        this.studentRepo = studentRepo;
        this.facultyRepo = facultyRepo;
        this.courseRepo = courseRepo;
    }
    
    @GetMapping("/")
    public String landing() {
        return "landing";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication auth) {
        String username = auth.getName();
        var user = userRepo.findByUsername(username);
        
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            model.addAttribute("role", user.get().getRole());
            model.addAttribute("totalStudents", studentRepo.count());
            model.addAttribute("totalFaculty", facultyRepo.count());
            model.addAttribute("totalCourses", courseRepo.count());
        }
        
        return "dashboard";
    }
}
