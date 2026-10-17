package com.project.sms.service.impl;

import com.project.sms.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional // use @Transactional to ensure multiple database operations are executed as a single unit
               // @Transactional handles commit and rollback operations
public class StudentServiceImpl implements StudentService {
}
