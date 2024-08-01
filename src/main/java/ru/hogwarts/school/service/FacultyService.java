package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyService {
    Faculty create(Faculty faculty);

    Faculty read(Long id);

    Faculty update(Long id, Faculty faculty);

    void delete(Long id);

    Collection<Faculty> getAllFaculty();

    Collection<Faculty> filterByColor(String color);
}
