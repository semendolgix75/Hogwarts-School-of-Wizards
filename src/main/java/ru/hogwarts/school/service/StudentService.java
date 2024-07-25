package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;

@Service
public class StudentService {
    private final HashMap<Long, Student> students = new HashMap<>();
    private long lastId=0;

    public Student createStudent(Student student) {
        student.setId(++lastId);
        students.put(lastId,student);
        return student;
    }

    private Student findStudent(long id) {
        return students.get(id);
    }

    private Student editStudent(Student student) {
        return students.put(student.getId(),student);
    }

    private Student deleteStudent(long id) {
        return students.remove(id);
    }


}
