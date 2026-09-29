package com.project.sms.repository;

import com.project.sms.model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Courses, Long> {

    // whether input is in lowercase or uppercase, it should match the alphabet
    boolean existsByCourseCodeIgnoreCase (String code);
}
