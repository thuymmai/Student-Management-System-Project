package com.project.sms.repository;

import com.project.sms.model.Students;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Students, Long> {

    // a method that checks if email address is unique
    boolean existsByEmailIgnoreCase(String email);

    // create a method for pagination
    // return type is Page
    Page<Students> findByActiveTrue(Pageable pageable);

}
