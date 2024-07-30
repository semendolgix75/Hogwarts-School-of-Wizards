package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();
    private Long counter = 0L;

    public Faculty create(Faculty faculty) {
        Long currentId=++counter;
        faculty.setId(currentId);
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty read(Long id) {

        return faculties.get(id);
    }

    public Faculty update(Long id, Faculty faculty) {
        Faculty facultyFromDb=faculties.get(id);
        if (faculties.containsKey(facultyFromDb.getId())) {
            facultyFromDb.setName(faculty.getName());
            facultyFromDb.setColor(faculty.getColor());
        }
        return faculty;
    }

    public Faculty delete(Long id) {
        return faculties.remove(id);
    }

    public Collection<Faculty> getAllFaculty() {
        return faculties.values();
    }
    public Collection<Faculty> filterByColor(String color) {



//        students.values().stream()
//                .filter(s -> s.getAge() == age)
//                .map(Student::getName)
//                .collect(Collectors.toList());

        return faculties.values()
                .stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .collect(Collectors.toList());
//
    }
}
