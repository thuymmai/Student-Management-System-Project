package com.project.sms.repository;

import com.project.sms.model.Courses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Courses, Long> {

    // whether input is in lowercase or uppercase, it should match the alphabet
    boolean existsByCourseCodeIgnoreCase (String code);

    // method that only displays active courses
    // in MySQL, it will look like this: SELECT * FROM ___
    // F2 = true?? What is F2?
    Page<Courses> findByActiveTrue(Pageable pageable);
}
