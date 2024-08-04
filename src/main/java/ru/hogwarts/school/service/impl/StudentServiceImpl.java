package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {


    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student create(Student student) {

        return studentRepository.save(student);
    }
    @Override
    public Student read(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public Student update(Long id, Student student) {
        return studentRepository.findById(id).map(studentFromDb ->{
            studentFromDb.setName((student.getName()));
            studentFromDb.setAge((student.getAge()));
            return studentFromDb;
        } ).orElse(null);
    }

    @Override
    public Student delete(Long id) {
        return studentRepository.findById(id).map(student-> {
            studentRepository.deleteById(id);
            return student;
        }).orElse(null);
    }

    @Override
    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public List<Student> filterByAge(int age) {
        return studentRepository.findAllByAge(age);
    }


    public Collection<Student> findAllByAgeBetween(int minAge, int maxAge) {
        return studentRepository.findAllByAgeBetween(minAge, maxAge);
    }
}


//public Collection<Student> getAllStudents() {
//    return students.values();
//}
//
//public Collection<Student> filterByAge(int age) {
//
//    return students.values()
//            .stream()
//            .filter(s -> s.getAge() == age)
//            .collect(Collectors.toList());


//
//}
//}

