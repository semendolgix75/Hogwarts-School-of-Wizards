package ru.hogwarts.school.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class StudentControllerIntegrationTest {
    @LocalServerPort
    private int port;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void clearDatabase() {
        studentRepository.deleteAll();
    }

    @Test
    void shouldCreateStudent() {
        //given
        Student student = new Student("TestStudent", 20);
        //when
        ResponseEntity<Student> studentResponseEntity = restTemplate.postForEntity("http://localhost:"
                + port + "/students", student, Student.class);
        //then
        assertNotNull(studentResponseEntity);   //Получили ответ
        assertEquals(studentResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));  //получаем код 200(ответ верный)
        Student actualStudent = studentResponseEntity.getBody();    //получаем тело ответа

        if (actualStudent != null) {
            assertNotNull(actualStudent.getId());       //проверяем что у Student  появился Id
        }
        assert actualStudent != null;
        assertEquals(actualStudent.getName(), student.getName());   //проверяем что имя заданое, совпадает с именем возвращенным сервером
        assertThat(actualStudent.getAge()).isEqualTo(student.getAge()); //возраст совпадает с возвращенным ответом

    }

    @Test
    void shouldReadStudent() {
        //given
        Student student = new Student("TestStudent", 20);
        student = studentRepository.save(student);
        //when
        ResponseEntity<Student> studentResponseEntity = restTemplate.getForEntity("http://localhost:"
                + port + "/students/" + student.getId(), Student.class);
        //then
        assertNotNull(studentResponseEntity);   //Получили ответ
        assertEquals(studentResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));  //получаем код 200(ответ верный)

        Student actualStudent = studentResponseEntity.getBody();    //получаем тело ответа
        if (actualStudent != null) {
            assertNotNull(actualStudent.getId());       //проверяем что у Faculty есть Id
        }
        assert actualStudent != null;
        assertEquals(actualStudent.getName(), student.getName());   //проверяем что имя заданое, совпадает с именем возвращенным сервером
        assertThat(actualStudent.getAge()).isEqualTo(student.getAge()); //цвет совпадает с возвращенным ответом

    }

    @Test
    void shouldUpdateStudent() {
        //given
        Student student = new Student(1L, "Teststudent", 20);
        student = studentRepository.save(student);
        Student studentUpdate = new Student(1L, "Newstudent", 20);

        //when
        HttpEntity<Student> HttpEntity = new HttpEntity<>(studentUpdate);
        ResponseEntity<Student> studentResponseEntity=restTemplate.exchange("http://localhost:"
                + port + "/students/" + student.getId(), HttpMethod.PUT,
                HttpEntity,Student.class);
                //then
        assertNotNull(studentResponseEntity);   //Получили ответ
        assertEquals(studentResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));  //получаем код 200(ответ верный)

        Student actualStudent = studentResponseEntity.getBody();    //получаем тело ответа
        if (actualStudent != null) {
            assertNotNull(actualStudent.getId());       //проверяем что у Student есть Id
        }
        assert actualStudent != null;
        assertEquals(actualStudent.getName(), student.getName());   //проверяем что имя заданое, совпадает с именем возвращенным сервером
        assertThat(actualStudent.getAge()).isEqualTo(student.getAge()); //возраст совпадает с возвращенным ответом

    }
//    @Test
//    void shouldDeleteFaculty() {
//        //given
//        Faculty faculty = new Faculty(1L, "TestFaculty", "TestColor");
//        faculty = facultyRepository.save(faculty);
//
//
//        //when
//
//        ResponseEntity<Faculty> facultyResponseEntity=restTemplate.exchange("http://localhost:"
//                        + port + "/faculties/" + faculty.getId(), HttpMethod.DELETE,
//                null,Faculty.class);
//        //then
//        assertNotNull(facultyResponseEntity);   //Получили ответ
//        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));  //получаем код 200(ответ верный)
//
//        assertThat(facultyRepository.findById(faculty.getId())).isNotPresent(); //Элемент по ID не не найден'
//
//    }
}
