package ru.hogwarts.school.controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student editStudent = studentService.editStudent(student);
        if (editStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(editStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Student>> findAllStudent(@RequestParam(required = false) Integer age,
                                                        @RequestParam(required = false) Integer minAge,
                                                        @RequestParam(required = false) Integer maxAge,
                                                        @RequestParam(required = false) @PathVariable(name = "id") Long id) {
        if (age != null) {
            return ResponseEntity.ok(studentService.ageFilter(age));
        }
        if (minAge != null && maxAge != null) {
            return ResponseEntity.ok(studentService.betweenAgeFilter(minAge, maxAge));
        }
        if (id != null) {
            return ResponseEntity.ok(studentService.getStudentByFaculty(id));
        }
        return ResponseEntity.ok(studentService.allStudent());
    }

    @GetMapping("/school")
    public ResponseEntity getInfoAboutSchool() {
        return ResponseEntity.ok("Hogwarts it the beast school in the world!");
    }

}
