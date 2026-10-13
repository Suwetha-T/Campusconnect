package com.sms.backend.service;

import com.sms.backend.dto.response.StudentResponse;
import com.sms.backend.entity.Department;
import com.sms.backend.entity.Student;
import com.sms.backend.entity.User;
import com.sms.backend.enums.Role;
import com.sms.backend.repository.DepartmentRepository;
import com.sms.backend.repository.StudentRepository;
import com.sms.backend.repository.UserRepository;
import com.sms.backend.service.impl.StudentServiceImpl;
import com.sms.backend.util.SecurityUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void getCurrentStudentProfile_shouldCreateMissingProfileForCurrentUser() {
        User user = new User();
        user.setId(10L);
        user.setEmail("student@sms.com");
        user.setRole(Role.STUDENT);

        Department department = new Department();
        department.setId(5L);
        department.setName("Computer Science");

        when(userRepository.findByEmail("student@sms.com")).thenReturn(Optional.of(user));
        when(studentRepository.findByUser_Email("student@sms.com")).thenReturn(Optional.empty());
        when(departmentRepository.findAll()).thenReturn(List.of(department));
        when(studentRepository.save(any(Student.class))).thenAnswer(invocation -> {
            Student student = invocation.getArgument(0);
            student.setId(99L);
            return student;
        });

        try (MockedStatic<SecurityUtils> mockedSecurityUtils = org.mockito.Mockito.mockStatic(SecurityUtils.class)) {
            mockedSecurityUtils.when(SecurityUtils::getCurrentUserEmail).thenReturn("student@sms.com");

            StudentResponse response = studentService.getCurrentStudentProfile();

            assertNotNull(response);
            assertEquals("student@sms.com", response.getUserEmail());
            assertEquals("Computer Science", response.getDepartmentName());
        }
    }
}
