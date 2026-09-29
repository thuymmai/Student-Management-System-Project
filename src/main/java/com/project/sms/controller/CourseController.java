package com.project.sms.controller;

import com.project.sms.dto.CourseDTO;
import com.project.sms.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/course")
public class CourseController {

    // constructor injection
    // then created the courseService object
    private CourseService courseService;

    CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /*************************************************************************/

    @GetMapping("/new")
    public String showCreateCourse(Model model) {
        model.addAttribute("courseDto", new CourseDTO());
        return "add-course";
    }

    @GetMapping("/list")
    public String listCourses(Model model) {
        return "courses";
    }

    @PostMapping
    public String createCourse(@Valid @ModelAttribute("courseDto") CourseDTO courseDTO,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        // when a form is submitted, there might be errors so send it back here and display an error message
        // if there is an error, user will be directed to Add Course page
        // if validation fails, BindingResult will capture it
        if (bindingResult.hasErrors()) {
            return "add-course";
        }


        // check whether a code (?) already existed
        // have the boolean method in CourseService.java
        if (courseService.existsByCourseCode(courseDTO.getCourseCode())) {
            bindingResult.rejectValue("courseCode", null, "The course code must be unique");
            return "add-course";

        }

        courseService.createCourse(courseDTO);
        redirectAttributes.addAttribute("message", "Course created successfully.");

        return "/course/list"; // decide which page the method will direct later
    }
}
