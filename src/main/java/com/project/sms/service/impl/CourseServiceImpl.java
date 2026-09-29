package com.project.sms.service.impl;

import com.project.sms.dto.CourseDTO;
import com.project.sms.model.Courses;
import com.project.sms.repository.CourseRepository;
import com.project.sms.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ModelMapper mapper; //create ModelMapper's object below

    // created constructor
    CourseServiceImpl(CourseRepository courseRepository, ModelMapper mapper) {
        this.courseRepository = courseRepository;
        this.mapper = mapper;

    }

    @Override
    public CourseDTO createCourse(CourseDTO courseDTO) {

        // the object is Courses
        // convert courseDTO into Courses
        Courses courses = mapper.map(courseDTO, Courses.class);
        courseRepository.save(courses);
        return mapper.map(courses, CourseDTO.class); // convert entity back to CourseDTO
    }

    // check for ...
    // create a method in CourseRepository.java
    @Override
    public boolean existsByCode(String code) {
        return courseRepository.existsByCodeIgnoreCases(code);
    }
}
