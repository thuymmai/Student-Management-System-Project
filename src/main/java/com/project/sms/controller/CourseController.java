package com.project.sms.controller;

import com.project.sms.dto.CourseDTO;
import com.project.sms.exception.GlobalExceptionHandler;
import com.project.sms.service.CourseService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.security.authorization.RequiredAuthoritiesAuthorizationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/course")
public class CourseController {

    // created a variable called Logger
    private static final Logger log = LoggerFactory.getLogger(CourseController.class);

    // constructor injection
    // then created the courseService object
    // use final here to make sure courseService object is used in CourseController (right below)
    private final CourseService courseService;

    CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /*************************************************************************/

    @GetMapping("/new")
    public String showCreateCourse(Model model) {

        // call Get method, it is called from within "/course" (line 19)
        log.info("Get /course/new - showing create course page.");
        model.addAttribute("courseDto", new CourseDTO());
        return "add-course";
    }

    @GetMapping("/list")
    // defaultValue = 0 because page number in the pagination starts at 0
    public String listCourses(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "3") int size,
                              Model model,
                              @RequestParam(value = "message", required = false) String message) {

        log.info("Get /course/list - showing list of course page.");

        // 2) call method ___ from CourseServiceImpl.java

        // 1) before pagination, check if the list is coming/existing; if list exists, I will program for the pagination part.
        // first check if the list is getting displayed, so call courseService
        Page<CourseDTO> courses = courseService.getCourses(page, size);
        model.addAttribute("courses", courses);
        model.addAttribute("message", message);

        return "courses";
    }

    @PostMapping
    public String createCourse(@Valid @ModelAttribute("courseDto") CourseDTO courseDTO,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        log.info("Post /course - create course request received.");

        // when a form is submitted, there might be errors so send it back here and display an error message
        // if there is an error, user will be directed to Add Course page
        // if validation fails, BindingResult will capture it
        if (bindingResult.hasErrors()) {
            log.error("Post /course - page return due to validation error.");
            return "add-course";
        }


        // check whether a code (?) already existed
        // have the boolean method in CourseService.java
        if (courseService.existsByCourseCode(courseDTO.getCourseCode())) {
            log.error("Post /course - The course code must be unique.");
            bindingResult.rejectValue("courseCode", null, "The course code must be unique");
            return "add-course";

        }

        courseService.createCourse(courseDTO);
        redirectAttributes.addAttribute("message", "Course created successfully.");

        log.info("Post /course - create course request received.");

        return "redirect:/course/list"; // decide which page the method will direct later
    }

    @GetMapping("/{id}")
    public String getCourseById(@PathVariable Long id, Model model) {
        CourseDTO course = courseService.getCourseById(id);
        model.addAttribute("course", course);

        return "view-course";
    }

    @GetMapping("/{id}/edit")
    public String editCourse(@PathVariable Long id, Model model) {
        CourseDTO course = courseService.getCourseById(id);
        model.addAttribute("courseDto", course);

        return "edit-course";

    }

    @PostMapping("/{id}/update")
    public String updateCourse(@PathVariable Long id, @Valid @ModelAttribute("courseDto") CourseDTO courseDTO,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        log.info("Post /{id}/update - update course request received.", id);

        // when a form is submitted, there might be errors so send it back here and display an error message
        // if there is an error, user will be directed to Add Course page
        // if validation fails, BindingResult will capture it
        if (bindingResult.hasErrors()) {
            log.error("Post /{id}/update - page return due to validation error.");
            return "edit-course";
        }


        // check whether a code (?) already existed
        // have the boolean method in CourseService.java
        // change existsByCourseCode to existsByCourseCodeAndIdNot
        // also, provide id from updateCourse method above
        if (courseService.existsByCourseCodeAndIdNot(courseDTO.getCourseCode(), id)) {
            log.error("Post /{id}/update - The course code must be unique.");

            bindingResult.rejectValue("courseCode", null, "The course code must be unique");
            return "edit-course";

        }

        courseService.updateCourse(id, courseDTO);
        redirectAttributes.addAttribute("message", "Course is updated successfully.");

        log.info("Post /{id}/update - update course successfully.");

        return "redirect:/course/list";

    }
}
