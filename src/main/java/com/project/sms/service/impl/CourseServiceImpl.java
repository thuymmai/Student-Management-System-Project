package com.project.sms.service.impl;

import com.project.sms.dto.CourseDTO;
import com.project.sms.exception.GlobalExceptionHandler;
import com.project.sms.model.Courses;
import com.project.sms.repository.CourseRepository;
import com.project.sms.service.CourseService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private static final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);

    private final CourseRepository courseRepository;
    private final ModelMapper mapper; //create ModelMapper's object below

    // created constructor
    CourseServiceImpl(CourseRepository courseRepository, ModelMapper mapper) {
        this.courseRepository = courseRepository;
        this.mapper = mapper;

    }

    @Override
    public CourseDTO createCourse(CourseDTO courseDTO) {

        // whatever course code is created, the successful message will show here, along with that unique code
        log.info("creating course with code: {}", courseDTO.getCourseCode());

        // the object is Courses
        // convert courseDTO into Courses
        Courses courses = mapper.map(courseDTO, Courses.class);
        courseRepository.save(courses);
        return mapper.map(courses, CourseDTO.class); // convert entity back to CourseDTO
    }

    // create a method in CourseRepository.java
    @Override
    public boolean existsByCourseCode(String code) {
        // don't put sensitive information in the log
        log.info("checking if the course code exists: {}", code);

        return courseRepository.existsByCourseCodeIgnoreCase(code);
    }
}
