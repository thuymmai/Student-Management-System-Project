package com.project.sms.service;

import com.project.sms.dto.EnrollmentDTO;

public interface EnrollmentService {

    void enrollStudentToCourses(EnrollmentDTO enrollmentDTO);
}
