package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student create(Student student) {
        student.setId((null));
        logger.info("Отработал метод create");

        return studentRepository.save(student);
    }

    @Override
    public Student read(Long id) {

        logger.info("Отработал метод 'read'");
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public Student update(Long id, Student student) {

        logger.info("Отработал метод 'update'");
        return studentRepository.findById(id).map(studentFromDb -> {
            studentFromDb.setName((student.getName()));
            studentFromDb.setAge((student.getAge()));
            return studentFromDb;
        }).orElse(null);
    }

    @Override
    public Student delete(Long id) {

        logger.info("Отработал метод 'delete'");
        return studentRepository.findById(id).map(student -> {
            studentRepository.deleteById(id);
            return student;
        }).orElse(null);
    }

    @Override
    public Collection<Student> getAllStudents() {

        logger.info("Отработал метод 'getAllStudents'");
        return studentRepository.findAll();
    }

    @Override
    public List<Student> filterByAge(int age) {

        logger.info("Отработал метод 'filterByAge'");

        return studentRepository.findAllByAge(age);
    }

    public Collection<Student> findAllByAgeBetween(int minAge, int maxAge) {

        logger.info("Отработал метод 'findAllByAgeBetween'");
        return studentRepository.findAllByAgeBetween(minAge, maxAge);
    }

    @Override
    public Faculty getFaculty(Long studentId) {

        logger.info("Отработал метод 'getFaculty'");
        return studentRepository.findById(studentId)
                .map(Student::getFaculty)
                .orElse(null);

    }

    public Student findStudent(Long id) {

        logger.info("Отработал метод 'findStudent'");
        return studentRepository.getById(id);
    }

    @Override
    public Integer getAllStudentCount() {

        logger.info("Отработал метод 'getAllStudentCount'");
        return studentRepository.getAllStudentCount();
    }

    @Override
    public Double getAverageAgeStudent() {

        logger.info("Отработал метод 'getAverageAgeStudent'");
        return studentRepository.getAverageAgeStudent();
    }

    @Override
    public List<Student> getLastFiveByIdStudent() {

        logger.info("Отработал метод 'getLastFiveByIdStudent'");
        return studentRepository.getLastFiveByIdStudent();
    }


    //    Добавить эндпоинт для получения всех имен всех студентов, чье имя начинается с буквы А.
//    В ответе должен находиться отсортированный в алфавитном порядке список с именами в верхнем регистре.
//    Для получения всех студентов из базы использовать метод репозитория - findAll().
    @Override
    public List<String> getAllStudentNameBeginWithLetterA() {

        logger.info("Отработал метод для получения всех имен всех студентов, чье имя начинается с буквы А");
        return studentRepository.findAll()
                .stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(name -> name.startsWith("A"))
                .sorted()
                .collect(Collectors.toList());

    }

    //    Создать эндпоинт, который будет возвращать средний возраст всех студентов.
//    Для получения информации о всех студентах опять же следует использовать метод репозитория - findAll().
    @Override
    public Double getAverageAgeAllStudentStream() {
//
        logger.info("Отработал метод , который будет возвращать средний возраст всех студентов.");
        return studentRepository.findAll()
                .stream()
                .mapToDouble(Student::getAge)
                .average()
                .orElse(0);

    }
}





