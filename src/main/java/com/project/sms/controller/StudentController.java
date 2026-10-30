package com.project.sms.controller;

import com.project.sms.dto.CourseDTO;
import com.project.sms.dto.StudentDTO;
import com.project.sms.service.StudentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    public String listStudent(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "3") int size,
                              Model model,
                              @RequestParam(value = "message", required = false) String message) {

        log.info("Get /list - showing List Student page");

        // 2) call method ___ from CourseServiceImpl.java

        // 1) before pagination, check if the list is coming/existing; if list exists, I will program for the pagination part.
        // first check if the list is getting displayed, so call studentService
        Page<StudentDTO> students = studentService.getStudents(page, size);
        model.addAttribute("students", students);
        model.addAttribute("message", message);

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
        redirectAttributes.addAttribute("message", "Student record is added successfully");


        // redirect student to the student list page
        return "redirect:/students/list";

    }

    @GetMapping("/{id}")
    public String getStudentById(@PathVariable Long id, Model model) {
        StudentDTO student = studentService.getStudentById(id);
        model.addAttribute("student", student);

        return "view-student";
    }

    // create this method so Edit button can redirect to another page
    @GetMapping("/{id}/edit")
    public String editStudent(@PathVariable Long id, Model model) {
        StudentDTO student = studentService.getStudentById(id);
        model.addAttribute("studentDto", student);

        return "edit-student";
    }

    @PostMapping("/{id}/update")
    public String updateStudent(@PathVariable Long id,
                                @Valid @ModelAttribute("studentDto") StudentDTO studentDTO,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        log.info("Post /update - Update student request received");

        // if validation fails
        if (bindingResult.hasErrors()) {
            return "edit-student";
        }

        if (studentService.existsByEmailIgnoreCaseAndIdNot(studentDTO.getEmail(), id)) {

            log.error("Post /update - Email address must be unique.");
            bindingResult.rejectValue("email", null, "Email address must be unique");

            return "edit-student";
        }

        // if both things or fields are correct, save the info
        studentService.updateStudent(id, studentDTO);

        // redirect message
        redirectAttributes.addAttribute("message", "Student record is updated successfully");


        // redirect student to the student list page
        return "redirect:/students/list";
    }
}
