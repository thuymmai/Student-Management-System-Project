package com.project.sms.controller;

import com.project.sms.dto.CourseDTO;
import com.project.sms.dto.EnrollmentDTO;
import com.project.sms.service.CourseService;
import com.project.sms.service.EnrollmentService;
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
    public String enrollmentList(Model model) {

        log.info("Get /enrollments/showEnroll - showing Enrollment page.");
        return "enrolled-students";
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

        return "enroll-course";
    }
}
