package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service

public class StudentServiceImpl implements StudentService {


    private final Map<Long, Student> students = new HashMap<>();
    private Long counter = 0L;
    @Override
    public Student create(Student student) {
        Long currentId = ++counter;
        student.setId(currentId);
        students.put(student.getId(), student);
        return student;
    }

    @Override
    public Student read(Long id)
    {
        return students.get(id);
    }

    @Override
    public Student update(Long id, Student student) {
        if (students.containsKey(student.getId())) {
            Student studentFromDb = students.get(id);
            studentFromDb.setName(student.getName());
            studentFromDb.setAge(student.getAge());
            return studentFromDb;
        } else return null;
    }


public Student delete(long id)
{
    return students.remove(id);
}


public Collection<Student> getAllStudents() {
    return students.values();
}

public Collection<Student> filterByAge(int age) {

    return students.values()
            .stream()
            .filter(s -> s.getAge() == age)
            .collect(Collectors.toList());


    //
}
}

