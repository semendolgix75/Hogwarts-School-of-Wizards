package ru.hogwarts.school.service;

import org.springframework.web.bind.annotation.PathVariable;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentService {
    Student create(Student student);

    Student read(Long id);

    Student update(Long id, Student student);

    Student delete(Long id);

    Collection<Student> getAllStudents();

    Collection<Student> filterByAge(int age);


    Collection<Student> findAllByAgeBetween(int minAge, int maxAge);
    Faculty getFaculty(Long StudentId);
}
