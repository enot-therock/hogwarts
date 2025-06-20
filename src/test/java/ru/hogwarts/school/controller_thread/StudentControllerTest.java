package ru.hogwarts.school.controller_thread;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {

    @Mock
    private StudentService studentService;
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentController studentController;

    @Test
    void testParallelThread() {
        Student student1 = new Student();
        Student student2 = new Student();
        Student student3 = new Student();
        Student student4 = new Student();
        Student student5 = new Student();
        Student student6 = new Student();

        student1.setName("Vova");
        student2.setName("Aba");
        student3.setName("Eva");
        student4.setName("Harry");
        student5.setName("Barry");
        student6.setName("Alfred");

        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        students.add(student5);
        students.add(student6);

        when(studentController.getParallelThreadAllStudents()).thenReturn(students);
        List<Student> studentList = studentController.getParallelThreadAllStudents();

        Assertions.assertEquals(studentList, students);
        verify(studentService).getParallelThreadAllStudents();

        assertThat(studentController.getParallelThreadAllStudents().equals(HttpStatus.OK));
    }

    @Test
    void testParallelSynchronizedThread() {
        Student student1 = new Student();
        Student student2 = new Student();
        Student student3 = new Student();
        Student student4 = new Student();
        Student student5 = new Student();
        Student student6 = new Student();

        student1.setName("Vova");
        student2.setName("Aba");
        student3.setName("Eva");
        student4.setName("Harry");
        student5.setName("Barry");
        student6.setName("Alfred");

        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        students.add(student5);
        students.add(student6);

        when(studentController.getParallelSynchronizedThread()).thenReturn(students);
        List<Student> studentList = studentController.getParallelSynchronizedThread();

        Assertions.assertEquals(studentList, students);
        verify(studentService).getParallelSynchronizedThread();

        assertThat(studentController.getParallelSynchronizedThread().equals(HttpStatus.OK));
    }
}
