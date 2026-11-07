package com.project.sms.service.impl;

import com.project.sms.dto.EnrollmentDTO;
import com.project.sms.dto.EnrollmentSummaryDTO;
import com.project.sms.dto.StudentDTO;
import com.project.sms.model.Courses;
import com.project.sms.model.Enrollment;
import com.project.sms.model.Students;
import com.project.sms.repository.CourseRepository;
import com.project.sms.repository.EnrollmentRepository;
import com.project.sms.repository.StudentRepository;
import com.project.sms.service.EnrollmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private static final Logger log = LoggerFactory.getLogger(EnrollmentServiceImpl.class);

    // create the repository object
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    // create a constructor
    EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository,
                          StudentRepository studentRepository,
                          CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void enrollStudentToCourses(EnrollmentDTO enrollmentDTO) {
        log.info("Request from enrollStudentToCourses");

        Students student = studentRepository.findById(enrollmentDTO.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        for(Long courseId : enrollmentDTO.getCourseIds()) {
            Courses course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new RuntimeException("Course not found"));

            // created the existsByStudentIdAndCourseId method in the Enrollment repository
            if(enrollmentRepository.existsByStudentIdAndCourseId(enrollmentDTO.getStudentId(),
                    courseId)) {
                continue;
            }

            // created Enrollment object
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);

            // course (courseID) is saved here
            enrollmentRepository.save(enrollment);
        }

    }

    @Override
    public Page<EnrollmentSummaryDTO> getEnrolledStudents(int page, int size) {
        log.info("list of enrolled students from: {}", page);

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        return studentRepository.findEnrolledStudents(pageRequest)
                .map(student -> {
                    EnrollmentSummaryDTO dto = new EnrollmentSummaryDTO();
                    dto.setStudentId(student.getId());
                    dto.setStudentName(student.getFirstName() + " " + student.getLastName());
                    dto.setEmail(student.getEmail());

                    dto.setCourseCount(student.getEnrollments().size());
                    BigDecimal totalFee = student.getEnrollments().stream()
                            .map(enrollment -> enrollment.getCourse().getFee())
                            .filter(fee -> fee != null)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    dto.setTotalFee(totalFee);

                    return dto;
                });
    }
}
