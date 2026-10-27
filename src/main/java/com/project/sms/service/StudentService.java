package com.project.sms.service;

import com.project.sms.dto.StudentDTO;
import org.springframework.data.domain.Page;

public interface StudentService {

    boolean existsByEmailIgnoreCase(String email);

    // a method returning StudentDTO
    StudentDTO createStudent(StudentDTO studentDTO);

    // the page will tell which page to start from
    // the size will tell me how much data of class to fetch -> fetching 10-15 students at a time
    // create getStudents() method
    // method returns Page and accesses StudentDTO, not Course entity
    Page<StudentDTO> getStudents(int page, int size);
}
