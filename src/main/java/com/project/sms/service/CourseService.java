package com.project.sms.service;

import com.project.sms.dto.CourseDTO;
import org.springframework.data.domain.Page;

public interface CourseService {

    CourseDTO createCourse(CourseDTO courseDTO);

    boolean existsByCourseCode (String code);

    // the page will tell which page to start from
    // the size will tell me how much data of class to fetch -> fetching 10-15 classes at a time
    // create getCourses() method
    // method returns Page and accesses CourseDTO, not Course entity
    Page<CourseDTO> getCourses(int page, int size);




}
