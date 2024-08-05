package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {
    List<Student> findAllByAge(int age);
    Collection<Student> findAllByAgeBetween(int minAge,int maxAge);

}
