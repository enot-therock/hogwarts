package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/studentQuery")
public class StudentControllerQuery {

    private final StudentService studentService;

    public StudentControllerQuery(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/allStudentCounter")
    public Integer getByAllStudent() {
        return studentService.getByAllStudent();
    }

    @GetMapping("/middleAgeStudent")
    public Integer getByMiddleAgeStudent() {
        return studentService.getByMiddleAgeStudent();
    }

    @GetMapping("/lastFiveStudent")
    public ResponseEntity<List<Student>> getLastFiveStudent() {
        return ResponseEntity.ok(studentService.getLastFiveStudent());
    }
}
