package com.project.sms.service.impl;

import com.project.sms.dto.StudentDTO;
import com.project.sms.model.Students;
import com.project.sms.repository.StudentRepository;
import com.project.sms.service.StudentService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional // use @Transactional to ensure multiple database operations are executed as a single unit
               // @Transactional handles commit and rollback operations
public class StudentServiceImpl implements StudentService {

    private static final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);

    // declare object of StudentRepository class
    private final StudentRepository studentRepository;

    private final ModelMapper mapper;

    // constructor injection
    // below is the class constructor for the StudentServiceImpl class
    public StudentServiceImpl (StudentRepository studentRepository, ModelMapper mapper) {
        this.studentRepository = studentRepository;
        this.mapper = mapper;
    }

    @Override
    public boolean existsByEmailIgnoreCase(String email) {
        log.info("email from Create Student"); // no need to store email here because it is sensitive data
        return studentRepository.existsByEmailIgnoreCase(email);
    }

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {

        log.info("saving student data");

        Students students = mapper.map(studentDTO, Students.class);
        Students saved = studentRepository.save(students);
        return mapper.map(saved, StudentDTO.class);
    }
}
