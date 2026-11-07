package com.project.sms.controller;

import com.project.sms.dto.CourseDTO;
import com.project.sms.dto.EnrollmentDTO;
import com.project.sms.dto.EnrollmentSummaryDTO;
import com.project.sms.dto.StudentDTO;
import com.project.sms.service.CourseService;
import com.project.sms.service.EnrollmentService;
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
@RequestMapping("/enrollments")
public class EnrollmentController {

    private static final Logger log = LoggerFactory.getLogger(EnrollmentController.class);

    // get these instance variables or objects (?) from CourseController and StudentController
    // dependency injection
    private final CourseService courseService;
    private final StudentService studentService;
    private final EnrollmentService enrollmentService;

    // then use a constructor injection here for those fields (instance variables)
    public EnrollmentController(CourseService courseService,
                                StudentService studentService,
                                EnrollmentService enrollmentService) {
        this.courseService = courseService;
        this.studentService = studentService;
        this.enrollmentService = enrollmentService;
    }

    // method that opens the "page"? like Enrollment page?
    @GetMapping("/showEnroll")
    public String showEnroll(Model model) {

        log.info("Get /enrollments/showEnroll - showing Enrollment page.");

        model.addAttribute("enrollmentDto", new EnrollmentDTO());
        model.addAttribute("courseList", courseService.getAllCourses());
        model.addAttribute("studentList", studentService.getAllStudents());
        return "enroll-course";
    }

    @GetMapping("/enrollmentList")
    public String enrollmentList(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "3") int size,
                              Model model,
                              @RequestParam(value = "message", required = false) String message) {

        log.info("Get /enrollmentList - showing List of Enrolled Student page");

        Page<EnrollmentSummaryDTO> students = enrollmentService.getEnrolledStudents(page, size);
        model.addAttribute("students", students);
        model.addAttribute("message", message);

        return "enrolled-students"; // students.html
    }

    @PostMapping("/enrollCourse")
    public String enrollCourse(@Valid @ModelAttribute("enrollmentDto") EnrollmentDTO enrollmentDTO,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        log.info("Post /enrollments/enrollCourse - enrollment request received.");

        // before the user submits the request, check if his submission is valid
        if(bindingResult.hasErrors()) {

            model.addAttribute("courseList", courseService.getAllCourses());
            model.addAttribute("studentList", studentService.getAllStudents());

            return "enroll-course";
        }

        enrollmentService.enrollStudentToCourses(enrollmentDTO);
        redirectAttributes.addAttribute("message", "Enrolled successfully!");

        log.info("Post /enrollments/enrollCourse - Enrolled successfully.");

        return "redirect:/enrollments/enrollmentList";
    }
}
