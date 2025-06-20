package ru.hogwarts.school.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private final StudentRepository studentRepository;
    public final FacultyService facultyService;

    public StudentService(StudentRepository studentRepository, FacultyService facultyService) {
        this.studentRepository = studentRepository;
        this.facultyService = facultyService;
    }

    public Student createStudent(Student student) {
        logger.debug("The method for create Student, was called");
        Faculty currentFaculty = facultyService.getFacultyByName(student.getFaculty().getName());
        student.setFaculty(currentFaculty);
        logger.warn("Create student" + student);
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        logger.debug("The method for find Student, was called");
        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student) {
        logger.debug("The method for edit Student, was called");
        Faculty currentFaculty = facultyService.getFacultyByName(student.getFaculty().getName());
        student.setFaculty(currentFaculty);
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        logger.debug("The method for delete Student, was called");
        logger.warn("Student with " + id + " id was deleted");
        studentRepository.deleteById(id);
    }

    public List<Student> allStudent() {
        logger.debug("The method for getting all Student, was called");
        return studentRepository.findAll();
    }

    public List<Student> ageFilter(int age) {
        logger.debug("The method for getting Student with a determinate age, was called");
        return studentRepository.findByAge(age);
    }

    public List<Student> betweenAgeFilter(int minAge, int maxAge) {
        logger.debug("The method for getting Students with age between " + minAge + " and " + maxAge + ", was called");
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public List<Student> getStudentByFaculty(Long id) {
        logger.debug("The method for getting Students faculty, was called");
        Faculty currentFaculty = facultyService.findFaculty(id);
        return currentFaculty.getStudents();
    }

    public List<Student> getStudentName(String name) {
        logger.debug("The method for getting Student name, was called");
        return studentRepository.findByNameIgnoreCase(name);
    }

    public Integer getByAllStudent() {
        logger.debug("The method for getting quantity all Students, was called");
        return studentRepository.getByAllStudent();
    }

    public Integer getByMiddleAgeStudent() {
        logger.debug("The method for getting middle age Students, was called");
        return studentRepository.getByMiddleAgeStudent();
    }

    public List<Student> getLastFiveStudent() {
        logger.debug("The method for getting five last Students, was called");
        return studentRepository.getLastFiveStudent();
    }

    public List<Student> getAllStudentWhoseNameStartsWithA() {
        logger.debug("The method for getting all Students whose name starts with - A");
        return studentRepository.findAll().stream()
                    .filter(n -> n.getName().toUpperCase().startsWith("A"))
                    .sorted(Comparator.comparing(Student::getName))
                    .toList();
    }

    public Double getMiddleAgeStudent() {
        logger.debug("The method for getting middle age Students");
        return studentRepository.findAll().stream()
                .mapToDouble(Student::getAge).average().orElseThrow();
    }

    public List<Student> getParallelThreadAllStudents() {
        logger.debug("The method for getting list all student by using parallel thread");
        List<Student> students = studentRepository.findAll();

        System.out.println("Первый студент: " + students.get(0).getName());
        System.out.println("Второй студент: " + students.get(1).getName());

        new Thread(() -> {
            System.out.println("Третий студент: " + students.get(2).getName());
            System.out.println("Четвертый студент: " + students.get(3).getName());
        }).start();

        new Thread(() -> {
            System.out.println("Пятый студент: " + students.get(4).getName());
            System.out.println("Шестой студент: " + students.get(5).getName());
        }).start();

        return students;
    }

    public List<Student> getParallelSynchronizedThread() {
        logger.info("The method for getting all student by using" +
                " parallel synchronized thread");
        List<Student> students = studentRepository.findAll();

        System.out.println("Первый студент: " + students.get(0).getName());
        System.out.println("Второй студент: " + students.get(1).getName());

        new Thread(() -> {
            synchronized (students) {
                System.out.println("Третий студент: " + students.get(2).getName());
                System.out.println("Четвертый студент: " + students.get(3).getName());
            }
        }).start();

        new Thread(() -> {
            synchronized (students) {
                System.out.println("Пятый студент: " + students.get(4).getName());
                System.out.println("Шестой студент: " + students.get(5).getName());
            }
        }).start();

        return students;
    }

}
