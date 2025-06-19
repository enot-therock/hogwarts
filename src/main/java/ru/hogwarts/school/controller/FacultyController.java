package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty editFaculty = facultyService.editFaculty(faculty);
        if (editFaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(editFaculty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Faculty>> filterFaculty(@RequestParam(required = false) String color,
                                                       @RequestParam(required = false) String name) {
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(facultyService.nameFilter(name));
        }
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.colorFilter(color));
        }
        return ResponseEntity.ok(facultyService.AllFaculty());
    }

    @GetMapping("/student_faculty")
    public List<Student> getByStudentFaculty(String name) {
        return facultyService.getStudentFaculty(name);
    }

    @GetMapping("/longerNameFaculty")
    public Faculty getLongerNameFaculty() {
        return facultyService.getLongerNameFaculty();
    }

    @GetMapping("/faster_sum")
    public String getIntegerNumber() {
        return facultyService.getIntegerNumber();
    }

    @GetMapping("/sum_test")
    public String getSum() {
        return facultyService.getIntegerNumber_test();
    }
}
