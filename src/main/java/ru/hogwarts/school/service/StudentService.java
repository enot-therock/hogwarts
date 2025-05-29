package ru.hogwarts.school.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
@Transactional
public class StudentService {

    @Autowired
    private final StudentRepository studentRepository;
    public final FacultyService facultyService;

    public StudentService(StudentRepository studentRepository, FacultyService facultyService) {
        this.studentRepository = studentRepository;
        this.facultyService = facultyService;
    }

    public Student createStudent(Student student) {
        Faculty currentFaculty = facultyService.getFacultyByName(student.getFaculty().getName());
        student.setFaculty(currentFaculty);
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student) {
        Faculty currentFaculty = facultyService.getFacultyByName(student.getFaculty().getName());
        student.setFaculty(currentFaculty);
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    public List<Student> allStudent() {
        return studentRepository.findAll();
    }

    public List<Student> ageFilter(int age) {
        return studentRepository.findByAge(age);
    }

    public List<Student> betweenAgeFilter(int minAge, int maxAge) {
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public List<Student> getStudentByFaculty(Long id) {
        Faculty currentFaculty = facultyService.findFaculty(id);
        return currentFaculty.getStudents();
    }

}
