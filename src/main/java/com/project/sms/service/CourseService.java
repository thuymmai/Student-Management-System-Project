package com.project.sms.service;

import com.project.sms.dto.CourseDTO;

public interface CourseService {

    CourseDTO createCourse(CourseDTO courseDTO);

    boolean existsByCode (String code);




}
