package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentService {
    Student create(Student student);

    Student read(Long id);

    Student update(Long id, Student student);

    void delete(long id);

    Collection<Student> getAllStudents();

    Collection<Student> filterByAge(int age);
}
