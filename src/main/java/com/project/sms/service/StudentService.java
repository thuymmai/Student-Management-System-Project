package com.project.sms.service;

import com.project.sms.dto.StudentDTO;

public interface StudentService {

    boolean existsByEmailIgnoreCase(String email);

    // a method returning StudentDTO
    StudentDTO createStudent(StudentDTO studentDTO);
}
