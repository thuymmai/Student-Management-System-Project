package com.project.sms.controller;

import com.project.sms.dto.StudentDTO;
import com.project.sms.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/students")
public class StudentController {
    private static final Logger log = LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;

    // constructor injection
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/new") // retrieve read-only data, such as the add-student page so the user can add new students
    public String showCreateStudent(Model model) {

        log.info("Get /new - showing Create Student page");

        model.addAttribute("studentDto", new StudentDTO());

        return "add-student";
    }

    @GetMapping("/list")
    public String listStudent() {

        log.info("Get /list - showing List Student page");

        return "students"; // students.html
    }
}
