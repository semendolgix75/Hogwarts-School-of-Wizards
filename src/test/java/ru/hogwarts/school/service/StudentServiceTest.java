package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Student;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {
    private StudentService studentService;

    @BeforeEach
    public void clear() {
    studentService=null;
    }
    @Test
    void shouldCreateStudent() {
//  given
        Student expectedStudent = new Student(1L,"Student1", 20);
//  when
        Student actualStudent = studentService.create(expectedStudent);

//  then
        assertEquals(expectedStudent, actualStudent);


    }
    @Test
    void createStudent() {
// given

// when
// then
    }

    @Test
    void findStudent() {
    }

    @Test
    void editStudent() {
    }

    @Test
    void deleteStudent() {
    }

    @Test
    void getAllStudents() {
    }

    @Test
    void getStudentByAge() {
    }
}