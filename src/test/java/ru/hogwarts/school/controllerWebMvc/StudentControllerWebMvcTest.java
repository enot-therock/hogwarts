package ru.hogwarts.school.controllerWebMvc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentControllerWebMvcTest {

    @Mock
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentController studentController;

    @Test
    void testCreateStudent() throws Exception {
        // Given
        Student student = new Student();
        student.setName("Igor");
        student.setAge(23);

        //When
        when(studentService.createStudent(student)).thenReturn(student);
        Student newStudent = studentService.createStudent(student);

        //Then
        assertEquals(newStudent, student);
        verify(studentService, times(1)).createStudent(student);
    }


    @Test
    void testGetStudent() throws Exception {
        // Given
        Long id = 1L;

        Student student = new Student();
        student.setName("Igor");
        student.setAge(23);
        student.setId(id);

        // When
        when(studentService.findStudent(id)).thenReturn(student);
        Student newStudent = studentService.findStudent(id);

        ResponseEntity<Student> response = studentController.getStudent(id);

        // Then
        assertEquals(newStudent.getId(), id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testEditStudent() throws Exception {
        // Given
        Long id = 1L;

        Faculty faculty = new Faculty();
        faculty.setName("Harvard");

        Student student = new Student();
        student.setName("Igor");
        student.setAge(23);
        student.setFaculty(faculty);

        // When
        when(studentService.findStudent(id)).thenReturn(student);
        Student newStudent = studentService.findStudent(id);

        faculty.setName("Eagle");
        student.setFaculty(faculty);
        when(studentService.editStudent(student)).thenReturn(student);
        Student resultStudent = studentService.editStudent(student);

        ResponseEntity<Student> response = studentController.editStudent(student);

        // Then
        assertEquals(newStudent.getFaculty(), resultStudent.getFaculty());
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void testDeleteStudent() throws Exception {
        // Given
        Long id = 1L;

        Student student = new Student();
        student.setName("Igor");
        student.setAge(23);
        student.setId(id);

        studentService.deleteStudent(id);

        ResponseEntity<Student> response = studentController.deleteStudent(id);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
