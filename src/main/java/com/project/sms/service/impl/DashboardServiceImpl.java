package com.project.sms.service.impl;

import com.project.sms.dto.DashboardStatsDTO;
import com.project.sms.repository.CourseRepository;
import com.project.sms.repository.EnrollmentRepository;
import com.project.sms.repository.StudentRepository;
import com.project.sms.service.DashboardService;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    // create a constructor
    DashboardServiceImpl(EnrollmentRepository enrollmentRepository,
                          StudentRepository studentRepository,
                          CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public DashboardStatsDTO getDashboardStats() {

        long totalStudents = studentRepository.count();
        long totalCourses = courseRepository.count();

        String topPerformingCourse = getTopPerformingCourse();

        // calculate so I can pass start date and end date in countDistinctStudentByEnrollDateBetween() method
        YearMonth currentMonth = YearMonth.now();
        LocalDateTime startDate = currentMonth.atDay(1).atStartOfDay();
        LocalDateTime endDate = currentMonth.atEndOfMonth().atTime(LocalTime.MAX);

        // call the countDistinctStudentByEnrollDateBetween() method from EnrollmentRepository
        long studentEnrolledThisMonth = enrollmentRepository.countDistinctStudentByEnrollDateBetween(startDate, endDate);

        // create DashboardStatsDTO object
        DashboardStatsDTO dashboardStatsDTO = new DashboardStatsDTO();
        dashboardStatsDTO.setTotalStudents(totalStudents);
        dashboardStatsDTO.setTotalCourses(totalCourses);
        dashboardStatsDTO.setTopPerformingCourse(topPerformingCourse);
        dashboardStatsDTO.setStudentsEnrolledThisMonth(studentEnrolledThisMonth);

        return dashboardStatsDTO;
    }

    private String getTopPerformingCourse() {

        return enrollmentRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(e -> e.getCourse().getCourseCode(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }
}
