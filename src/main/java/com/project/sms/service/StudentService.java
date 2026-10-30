package com.project.sms.service;

import com.project.sms.dto.StudentDTO;
import org.springframework.data.domain.Page;

public interface StudentService {

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);

    // a method returning StudentDTO
    StudentDTO createStudent(StudentDTO studentDTO);

    // the page will tell which page to start from
    // the size will tell me how much data of class to fetch -> fetching 10-15 students at a time
    // create getStudents() method
    // method returns Page and accesses StudentDTO, not Course entity
    Page<StudentDTO> getStudents(int page, int size);

    // create a method that get/retrieve student record by ID
    // where I return StudentDTO
    StudentDTO getStudentById(Long id);

    // create a method that updates student record
    // include it in StudentController
    StudentDTO updateStudent(Long id, StudentDTO studentDTO);
}
