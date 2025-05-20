package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;

public class FacultyService {

    private HashMap<Long, Faculty> facultys = new HashMap<>();
    private long counter = 0;

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(counter++);
        facultys.put(counter, faculty);
        return faculty;
    }

    public Faculty findFaculty(long id) {
        return facultys.get(id);
    }

    public Faculty editFaculty(Faculty faculty) {
        if (facultys.containsKey(faculty.getId())) {
            facultys.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }

    public Faculty deleteFaculty(long id) {
        return facultys.remove(id);
    }
}

