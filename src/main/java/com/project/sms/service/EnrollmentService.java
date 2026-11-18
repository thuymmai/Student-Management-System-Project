package com.project.sms.service;

import com.project.sms.dto.EnrollmentDTO;
import com.project.sms.dto.EnrollmentSummaryDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EnrollmentService {

    void enrollStudentToCourses(EnrollmentDTO enrollmentDTO);

    // returns the names of the selected courses that the student is already enrolled in
    // (an empty list means there are no duplicates)
    List<String> getAlreadyEnrolledCourseNames(EnrollmentDTO enrollmentDTO);

    Page<EnrollmentSummaryDTO> getEnrolledStudents(int page, int size);

    // will only get one detail each fetch, not getting multiple details at once
    EnrollmentSummaryDTO findEnrolledStudentCourseDetails(Long studentId);

    List<EnrollmentSummaryDTO> getRecentlyEnrolledStudents();
}
