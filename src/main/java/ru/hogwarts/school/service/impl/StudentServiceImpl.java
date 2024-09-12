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
    int count=0;
    public final Object flag = new Object();

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

    //    Эндпоинт должен выводить в консоль имена всех студентов в параллельном режиме, а именно:
//    первые два имени вывести в основном потоке
//    имена третьего и четвертого студента вывести в параллельном потоке
//    имена пятого и шестого студента вывести в еще одном параллельном потоке.
//    Для вывода используйте команду System.out.println().


@Override
    public void printAllStudentsInParallelMode() {

        List<Student> students = studentRepository.findAll();
        logger.info("Запущен метод printAllStudentsInParallelMode");
        System.out.println("Поток main");
        System.out.println(students.get(0).getName());
        System.out.println(students.get(1).getName());
        Thread t1 = new Thread(() -> {
            {
                System.out.println(students.get(2).getName());
                System.out.println(students.get(3).getName());
                ;
            }
        });
        Thread t2 = new Thread(() -> {
            {
                System.out.println(students.get(4).getName());
                System.out.println(students.get(5).getName());
            }
        });
        System.out.println("Поток 1");
        t1.start();
        System.out.println("Поток 2");
        t2.start();
    }

@Override
    public void printAllStudentsInSyncMode() {
        List<Student> students = studentRepository.findAll();
        logger.info("Запущен метод printAllStudentsInSyncMode");
        System.out.println("Поток main");
        printSteam(students.get(0));
        printSteam(students.get(1));
        new Thread(() -> {
            {
                printSteam(students.get(2));
                printSteam(students.get(3));

            }
        }).start();
        new Thread(() -> {
            {
                printSteam(students.get(4));
                printSteam(students.get(5));
            }
        }).start();


    }

@Override
    public void printSteam(Student student) {
        synchronized (flag) {
            System.out.println(count+" "+student.getName());
            count++;
        }
    }
}






