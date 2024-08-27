package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {
    List<Student> findAllByAge(int age);
    List<Student> findAllByAgeBetween(int minAge,int maxAge);
    @Query(value = "SELECT COUNT(*) from Student",nativeQuery = true)
    Integer getAllStudentCount();

    @Query(value = "SELECT AVG(age) from Student",nativeQuery = true)
    Double getAverageAgeStudent();

    @Query(value = "SELECT * FROM Student  ORDER BY id DESC LIMIT 5 ",nativeQuery = true)
    List<Student> getLastFiveByIdStudent();
}
