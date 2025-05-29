package ru.hogwarts.school.controllerWebMvc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FacultyControllerWebMvcTest {

    @Mock
    private FacultyService facultyService;

    @Mock
    private FacultyRepository facultyRepository;

    @InjectMocks
    private FacultyController facultyController;

    @Test
    void testCreateFaculty() throws Exception {
        // Given
        Faculty faculty = new Faculty();
        faculty.setName("Harvard");
        faculty.setColor("Green");

        //When
        when(facultyService.createFaculty(faculty)).thenReturn(faculty);
        Faculty newFaculty = facultyService.createFaculty(faculty);

        //Then
        assertEquals(faculty, newFaculty);
        verify(facultyService).createFaculty(faculty);
    }

    @Test
    void testGetFaculty() throws Exception {
        // Given
        Long id = 1L;

        Faculty faculty = new Faculty();
        faculty.setName("Harvard");
        faculty.setColor("Green");
        faculty.setId(id);

        //When
        when(facultyService.findFaculty(id)).thenReturn(faculty);
        Faculty newFaculty = facultyService.findFaculty(id);

        ResponseEntity<Faculty> response = facultyController.getFaculty(id);

        //Then
        assertEquals(faculty.getId(), id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testEditFaculty() throws Exception {
        // Given
        Long id = 1L;

        Faculty faculty = new Faculty();
        faculty.setName("Harvard");
        faculty.setColor("Green");
        faculty.setId(id);

        //When
        when(facultyService.findFaculty(id)).thenReturn(faculty);
        Faculty newFaculty = facultyService.findFaculty(id);

        faculty.setColor("Black");
        when(facultyService.editFaculty(faculty)).thenReturn(faculty);
        Faculty resultFaculty = facultyService.editFaculty(faculty);

        ResponseEntity<Faculty> response = facultyController.editFaculty(faculty);

        //Then
        assertEquals(newFaculty.getColor(), resultFaculty.getColor());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteFaculty() throws Exception {
        // Given
        Long id = 1L;

        Faculty faculty = new Faculty();
        faculty.setName("Harvard");
        faculty.setColor("Green");
        faculty.setId(id);

        facultyService.deleteFaculty(id);

        ResponseEntity<Faculty> response = facultyController.deleteFaculty(id);

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetStudentFaculty() throws Exception {
        // Given
        Long id = 1L;
        String name = "Harvard";
        List<Student> studentList = new ArrayList<>();

        Faculty faculty = new Faculty();
        faculty.setName("Harvard");
        faculty.setColor("Green");
        faculty.setId(id);

        Student student_1 = new Student(1L, "Igor", 23, faculty);

        studentList.add(student_1);

        //When
        when(facultyService.getStudentFaculty(faculty.getName())).thenReturn(List.of());
        List<Student> studentsFaculty = facultyService.getStudentFaculty(name);

        //Then
        assertEquals(studentList.contains(student_1.getFaculty()), studentsFaculty.contains(name));
    }
}
