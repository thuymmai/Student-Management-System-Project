package com.project.sms.controller;

import com.project.sms.dto.CourseDTO;
import com.project.sms.dto.StudentDTO;
import com.project.sms.service.StudentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @PostMapping("/save")
    public String createStudent (@Valid @ModelAttribute("studentDto") StudentDTO studentDTO,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        log.info("Post /save - Create Student request received");

        // if validation fails
        if (bindingResult.hasErrors()) {
            return "add-student";
        }

        if (studentService.existsByEmailIgnoreCase(studentDTO.getEmail())) {

            log.error("Post /save - Email address must be unique.");
            bindingResult.rejectValue("email", null, "Email address must be unique");

            return "add-student";
        }

        // if both things or fields are correct, save the info
        studentService.createStudent(studentDTO);

        // redirect message
        redirectAttributes.addAttribute("message", "Student record is updated successfully");


        // redirect student to the student list page
        return "redirect:/students/list";

    }
}
