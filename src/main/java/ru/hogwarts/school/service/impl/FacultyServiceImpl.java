package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.controller.InfoController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;


    private final Logger logger =  LoggerFactory.getLogger(FacultyServiceImpl.class);
    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty create(Faculty faculty) {

        logger.info("Отработал метод 'createFaculty'");
        return facultyRepository.save(faculty);
    }
    @Override
    public Faculty read(Long id) {

        logger.info("Отработал метод 'readFaculty'");
        return facultyRepository.findById(id).orElse(null);
    }
    @Override
    public Faculty update(Long id, Faculty faculty) {

        logger.info("Отработал метод 'updateFaculty'");
        return facultyRepository.findById(id).map(facultyFromDb->{
            facultyFromDb.setName(facultyFromDb.getName());
            facultyFromDb.setColor(facultyFromDb.getColor());
            facultyRepository.save(facultyFromDb);
            return facultyFromDb;
        }).orElse(null);
    }

    @Override
    public Faculty delete(Long id) {

        logger.info("Отработал метод 'deleteFaculty'");
        return facultyRepository.findById(id).map(faculty-> {
            facultyRepository.deleteById(id);
            return faculty;
        }).orElse(null);
    }
    public Collection<Faculty> getAllFaculty() {

        logger.info("Отработал метод 'getAllFaculty'");
        return facultyRepository.findAll();
    }

@Override
    public List<Faculty> filterByColor(String color) {
        logger.info("Отработал метод 'filterByColor'");
        return facultyRepository.findAllByColor(color);
    }

    @Override
    public Collection<Faculty> findAllByNameIgnoreCaseOrColorIgnoreCase(String name, String color) {
        logger.info("Отработал метод 'findAllByNameIgnoreCaseOrColorIgnoreCase'");
        return facultyRepository.findAllByNameIgnoreCaseOrColorIgnoreCase(name,color);
    }

    @Override
    public List<Student> getStudents(Long facultyId) {
                logger.info("Отработал метод 'getStudentsByFaculty'");
        return facultyRepository.findById(facultyId)
                .map(Faculty::getStudents)
                .orElse(null);
    }
}
