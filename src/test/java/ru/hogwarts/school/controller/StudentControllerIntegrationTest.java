package ru.hogwarts.school.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.ArrayList;
import java.util.List;

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
    private FacultyRepository facultyRepository;
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
    @Test
    void shouldDeleteStudent() {
        //given
        Student student = new Student(1L, "TestStudent", 20);
        student = studentRepository.save(student);


        //when

        ResponseEntity<Student> studentResponseEntity=restTemplate.exchange("http://localhost:"
                        + port + "/students/" + student.getId(), HttpMethod.DELETE,
                null,Student.class);
        //then
        assertNotNull(studentResponseEntity);   //Получили ответ
        assertEquals(studentResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));  //получаем код 200(ответ верный)

        assertThat(studentRepository.findById(student.getId())).isNotPresent(); //Элемент по ID не не найден'

    }
    @Test
    public void shouldGetAllStudent() {
//given
        Student student1 = new Student("TestStudent1", 20);
        Student student2 = new Student("TestStudent2", 30);
        student1 = studentRepository.save(student1);
        student2 = studentRepository.save(student2);
        List<Student> students=new ArrayList<Student>();
        students.add(student1);
        students.add(student2);
        //when
        String url = "http://localhost:" + port + "/students";
        ResponseEntity<List<Student>> facultyResponseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {}
        );

        students = facultyResponseEntity.getBody();
        //then
        assertNotNull(facultyResponseEntity);   //Получили ответ
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));  //получаем код 200(ответ верный)
        assert students != null;
        String w=students.get(0).getName();
        assertEquals("TestStudent1", students.get(0).getName());
        Student actualStudent1 = facultyResponseEntity.getBody().get(0);    //получаем тело ответа
        if (actualStudent1 != null) {
            assertNotNull(actualStudent1.getId());       //проверяем что у Faculty есть Id
        }
        assert actualStudent1 != null;
        assertEquals(actualStudent1.getName(), student1.getName());   //проверяем что имя заданое, совпадает с именем возвращенным сервером
        assertThat(actualStudent1.getAge()).isEqualTo(student1.getAge()); //цвет совпадает с возвращенным ответом
    }
    @Test
    public void shouldGetStudentByAgeBetween() {
//given
        Student student1 = new Student("TestStudent1", 20);
        Student student2 = new Student("TestStudent2", 30);
        student1 = studentRepository.save(student1);
        student2 = studentRepository.save(student2);
        int minAge=15;
        int maxAge=25;
        List<Student> students=new ArrayList<Student>();
        students.add(student1);
        students.add(student2);
        //when
        //http://localhost:8080/students/byAgeBetween?minAge=15&maxAge=25
        String url = "http://localhost:" + port + "/students/byAgeBetween?minAge="+minAge+"&maxAge="+maxAge;
        ResponseEntity<List<Student>> studentResponseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {}
        );

        students = studentResponseEntity.getBody();
        //then
        assertNotNull(studentResponseEntity);   //Получили ответ
        assertEquals(studentResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));  //получаем код 200(ответ верный)
        assert students != null;
        String w=students.get(0).getName();
        assertEquals("TestStudent1", students.get(0).getName());
        Student actualStudent1 = studentResponseEntity.getBody().get(0);    //получаем тело ответа
        if (actualStudent1 != null) {
            assertNotNull(actualStudent1.getId());       //проверяем что у Faculty есть Id
        }
        assert actualStudent1 != null;
        assertEquals(actualStudent1.getName(), student1.getName());   //проверяем что имя заданое, совпадает с именем возвращенным сервером
        assertThat(actualStudent1.getAge()).isEqualTo(student1.getAge()); //цвет совпадает с возвращенным ответом
    }

@Test
public void shouldGetStudentByFaculty() {
//given
    Faculty faculty1 = new Faculty("TestFaculty1", "TestColor1");
    Faculty faculty2 = new Faculty("TestFaculty2", "TestColor2");
    Student student1 = new Student(1L,"Name1" ,20);
    Student student2 = new Student(2L,"Name2" ,20);
    student1.setFaculty(faculty1);
    faculty1 = facultyRepository.save(faculty1);
    faculty2 = facultyRepository.save(faculty2);
    student1 = studentRepository.save(student1);
    student2 = studentRepository.save(student2);
    List<Faculty> faculties=new ArrayList<Faculty>();
    List<Student> students=new ArrayList<Student>();
    faculties.add(faculty1);
    faculties.add(faculty2);
    student1.setFaculty(faculty1);
    students.add(student1);
    students.add(student2);
    //when
    String url = "http://localhost:" + port + "/students/"+faculty1.getId()+"/faculty";
    ResponseEntity<List<Student>> studentResponseEntity = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Student>>() {}
    );
    students = studentResponseEntity.getBody();
    //then
    assertNotNull(studentResponseEntity);   //Получили ответ
    assertEquals(studentResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));  //получаем код 200(ответ верный)
    assert students != null;
    String w=students.get(0).getName();
    assertEquals("Name1", students.get(0).getName());
    Student actualStudent = studentResponseEntity.getBody().get(0);    //получаем тело ответа
    if (actualStudent != null) {
        assertNotNull(actualStudent.getId());       //проверяем что у Faculty есть Id
    }
    assert actualStudent != null;
    assertEquals(actualStudent.getName(), student1.getName());   //проверяем что имя заданое, совпадает с именем возвращенным сервером
}}
