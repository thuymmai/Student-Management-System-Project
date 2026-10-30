package com.project.sms.repository;

import com.project.sms.model.Students;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Students, Long> {

    // a method that checks if email address is unique
    boolean existsByEmailIgnoreCase(String email);

    // method checks if the same ID belongs to its corresponding entity/record
    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);

    // create a method for pagination
    // return type is Page
    Page<Students> findByActiveTrue(Pageable pageable);

}
