package com.project.sms.repository;

import com.project.sms.model.Students;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Students, Long> {

    // a method that checks if email address is unique
    boolean existsByEmailIgnoreCase(String email);

    // method checks if the same ID belongs to its corresponding entity/record
    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);

    // create a method for pagination
    // return type is Page
    Page<Students> findByActiveTrue(Pageable pageable);

    List<Students> findByActiveTrue();

    // this pagination query is for the list of enrolled students
    //@EntityGraph(attributePaths = {"enrollments", "enrollments.course"})
    @Query(value = """
            select distinct s
            from Students s
            join s.enrollments e
           """,
            countQuery = """
                select count(distinct s)
                from Students s
                join s.enrollments e
            """)
    Page<Students> findEnrolledStudents(Pageable pageable);

    // this method is for fetching course details and displaying them in Enrollment Details (when clicking on the blue Eye button in Enrolled Students page)
    @Query("""
                select s
                from Students s
                join fetch s.enrollments e
                join fetch e.course c
                where s.id = :id 
           """)
    Optional<Students> findEnrolledStudentCourseDetails(@Param("id") Long id);

}
