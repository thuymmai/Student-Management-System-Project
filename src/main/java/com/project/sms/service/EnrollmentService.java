package com.project.sms.service;

import com.project.sms.dto.EnrollmentDTO;
import com.project.sms.dto.EnrollmentSummaryDTO;
import org.springframework.data.domain.Page;

public interface EnrollmentService {

    void enrollStudentToCourses(EnrollmentDTO enrollmentDTO);

    Page<EnrollmentSummaryDTO> getEnrolledStudents(int page, int size);
}
