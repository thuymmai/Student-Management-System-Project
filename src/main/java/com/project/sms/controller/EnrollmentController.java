package com.project.sms.controller;

import com.project.sms.dto.EnrollmentDTO;
import com.project.sms.service.CourseService;
import com.project.sms.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/enrollments")
public class EnrollmentController {

    private static final Logger log = LoggerFactory.getLogger(EnrollmentController.class);

    // get these instance variables from CourseController and StudentController
    // dependency injection
    private final CourseService courseService;
    private final StudentService studentService;

    // then use a constructor injection here for those fields (instance variables)
    public EnrollmentController(CourseService courseService, StudentService studentService) {
        this.courseService = courseService;
        this.studentService = studentService;
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
}
