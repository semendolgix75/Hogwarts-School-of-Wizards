package ru.hogwarts.school.service;

import org.springframework.web.bind.annotation.PathVariable;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {
    Student create(Student student);

    Student read(Long id);

    Student update(Long id, Student student);

    Student delete(Long id);

    Collection<Student> getAllStudents();

    List<Student> filterByAge(int age);


    Collection<Student> findAllByAgeBetween(int minAge, int maxAge);
    Faculty getFaculty(Long StudentId);

    Student findStudent(Long studentId);
    Integer getAllStudentCount();

    Double getAverageAgeStudent();

    List<Student> getLastFiveByIdStudent();

    List<Student> getAllStudentNameBeginWithLetterA();
}
