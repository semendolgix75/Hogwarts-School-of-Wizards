package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;
    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty create(Faculty faculty) {
        return facultyRepository.save(faculty);
    }
    @Override
    public Faculty read(Long id) {
        return facultyRepository.findById(id).orElse(null);
    }
    @Override
    public Faculty update(Long id, Faculty faculty) {

        return facultyRepository.findById(id).map(facultyFromDb->{
            facultyFromDb.setName(facultyFromDb.getName());
            facultyFromDb.setColor(facultyFromDb.getColor());
            facultyRepository.save(facultyFromDb);
            return facultyFromDb;
        }).orElse(null);
    }

    @Override
    public Faculty delete(Long id) {
        return facultyRepository.findById(id).map(faculty-> {
            facultyRepository.deleteById(id);
            return faculty;
        }).orElse(null);
    }
    public Collection<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }
//    public Collection<Faculty> filterByColor(String color) {
//
//        return getAllFaculty()
//                .stream()
//                .filter(faculty -> faculty.getColor().equals(color))
//                .collect(Collectors.toList());
//
//    }
@Override
    public List<Faculty> filterByColor(String color) {
        return facultyRepository.findAllByColor(color);
    }

    @Override
    public Collection<Faculty> findAllByNameIgnoreCaseOrColorIgnoreCase(String name, String color) {
        return facultyRepository.findAllByNameIgnoreCaseOrColorIgnoreCase(name,color);
    }
}
