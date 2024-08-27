package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {


    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student create(Student student) {
        student.setId((null));
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

    @Override
    public Faculty getFaculty(Long studentId) {
        return studentRepository.findById(studentId)
                .map(Student::getFaculty)
                .orElse(null);

    }
    public Student findStudent(Long id) {
        return studentRepository.getById(id);
    }

    @Override
    public Integer getAllStudentCount() {
        return studentRepository.getAllStudentCount();
    }

    @Override
    public Double getAverageAgeStudent() {
        return studentRepository.getAverageAgeStudent();
    }

    @Override
    public List<Student> getLastFiveByIdStudent() {
        return studentRepository.getLastFiveByIdStudent();
    }

}





