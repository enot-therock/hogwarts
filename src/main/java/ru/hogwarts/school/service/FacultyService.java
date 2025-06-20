package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@Service
public class FacultyService {

    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    @Autowired
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.debug("The method for create Faculty, was called");
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        logger.debug("The method for find Faculty with id " + id + ", was called");
        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.debug("The method for edit Faculty, was called");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        logger.debug("The method for delete Faculty with id " + id + ", was called");
        facultyRepository.deleteById(id);
    }

    public List<Faculty> colorFilter(String color) {
        logger.debug("The method for getting Faculty with color " + color + ", was called");
        return facultyRepository.findByColorIgnoreCase(color);
    }

    public List<Faculty> nameFilter(String name) {
        logger.debug("The method for getting Faculty with " + name + ", was called");
        return facultyRepository.findByNameContainsIgnoreCase(name);
    }

    public List<Faculty> AllFaculty() {
        logger.debug("The method for getting all Faculty, was called");
        return facultyRepository.findAll();
    }

    public Faculty getFacultyByName(String name) {
        logger.debug("The method for find Faculty with " + name + ", was called");
        return facultyRepository.findByName(name);
    }

    public List<Student> getStudentFaculty(String name) {
        logger.debug("The method for getting Students faculty, was called");
        Faculty faculty = facultyRepository.findByName(name);
        return faculty.getStudents();
    }

    public Faculty getLongerNameFaculty() {
        logger.debug("The method for getting Faculty with Longer Name");
        return facultyRepository.findAll().stream()
                .max(Comparator.comparingInt(n -> n.getName().length()))
                .orElseThrow();
    }

    public String getIntegerNumber() {
        logger.debug("The method for getting integer number");
        long start = System.currentTimeMillis();
        Long sum = LongStream
                .rangeClosed(0, 1_000_000)
                .parallel()
                .reduce(0, Long::sum);
        long end = System.currentTimeMillis();
        long result = end - start;
        return "Время вычисления суммы чисел от 0 до 1_000_000 - " + result
                + " мс, результат: " + sum;
    }

    public String getIntegerNumber_test() {
        logger.debug("The method for getting integer number");
        long start = System.currentTimeMillis();
        Long sum = Stream
                .iterate(1L, a -> a + 1)
                .limit(1_000_000)
                .reduce(0L, (a, b) -> a + b);
        long end = System.currentTimeMillis();
        long result = end - start;
        return "Время вычисления суммы чисел от 0 до 1_000_000 - " + result
                + " мс, результат: " + sum;
    }
}

