package ru.hogwarts.school.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class FacultyControllerIntegrationTest {
    @LocalServerPort
    private int port;
    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void clearDatabase() {
        facultyRepository.deleteAll();
    }

    @Test
    void shouldCreateFaculty() {
        //given
        Faculty faculty = new Faculty("TestFaculty", "TestColor");
        //when
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.postForEntity("http://localhost:"
                + port + "/faculties", faculty, Faculty.class);
        //then
        assertNotNull(facultyResponseEntity);   //Получили ответ
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));  //получаем код 200(ответ верный)

        Faculty actualFaculty = facultyResponseEntity.getBody();    //получаем тело ответа
        if (actualFaculty != null) {
            assertNotNull(actualFaculty.getId());       //проверяем что у Faculty появился Id
        }
        assert actualFaculty != null;
        assertEquals(actualFaculty.getName(), faculty.getName());   //проверяем что имя заданое, совпадает с именем возвращенным сервером
        assertThat(actualFaculty.getColor()).isEqualTo(faculty.getColor()); //цвет совпадает с возвращенным ответом

    }

    @Test
    void shouldReadFaculty() {
        //given
        Faculty faculty = new Faculty("TestFaculty", "TestColor");
        faculty = facultyRepository.save(faculty);
        //when
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.getForEntity("http://localhost:"
                + port + "/faculties/" + faculty.getId(), Faculty.class);
        //then
        assertNotNull(facultyResponseEntity);   //Получили ответ
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));  //получаем код 200(ответ верный)

        Faculty actualFaculty = facultyResponseEntity.getBody();    //получаем тело ответа
        if (actualFaculty != null) {
            assertNotNull(actualFaculty.getId());       //проверяем что у Faculty есть Id
        }
        assert actualFaculty != null;
        assertEquals(actualFaculty.getName(), faculty.getName());   //проверяем что имя заданое, совпадает с именем возвращенным сервером
        assertThat(actualFaculty.getColor()).isEqualTo(faculty.getColor()); //цвет совпадает с возвращенным ответом

    }

    @Test
    void shouldUpdateFaculty() {
        //given
        Faculty faculty = new Faculty(1L, "TestFaculty", "TestColor");
        faculty = facultyRepository.save(faculty);
        Faculty facultyUpdate = new Faculty(1L, "NewFaculty", "NewColor");

        //when
        HttpEntity<Faculty> HttpEntity = new HttpEntity<>(facultyUpdate);
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.exchange("http://localhost:"
                        + port + "/faculties/" + faculty.getId(), HttpMethod.PUT,
                HttpEntity, Faculty.class);
        //then
        assertNotNull(facultyResponseEntity);   //Получили ответ
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));  //получаем код 200(ответ верный)

        Faculty actualFaculty = facultyResponseEntity.getBody();    //получаем тело ответа
        if (actualFaculty != null) {
            assertNotNull(actualFaculty.getId());       //проверяем что у Faculty есть Id
        }
        assert actualFaculty != null;
        assertEquals(actualFaculty.getName(), faculty.getName());   //проверяем что имя заданое, совпадает с именем возвращенным сервером
        assertThat(actualFaculty.getColor()).isEqualTo(faculty.getColor()); //цвет совпадает с возвращенным ответом

    }

    @Test
    void shouldDeleteFaculty() {
        //given
        Faculty faculty = new Faculty(1L, "TestFaculty", "TestColor");
        faculty = facultyRepository.save(faculty);

        //when

        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.exchange("http://localhost:"
                        + port + "/faculties/" + faculty.getId(), HttpMethod.DELETE,
                null, Faculty.class);
        //then
        assertNotNull(facultyResponseEntity);   //Получили ответ
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));  //получаем код 200(ответ верный)

        assertThat(facultyRepository.findById(faculty.getId())).isNotPresent(); //Элемент по ID не не найден'

    }

    @Test
    public void shouldGetAllFaculty() {
//given
        Faculty faculty1 = new Faculty("TestFaculty1", "TestColor1");
        Faculty faculty2 = new Faculty("TestFaculty2", "TestColor2");
        faculty1 = facultyRepository.save(faculty1);
        faculty2 = facultyRepository.save(faculty2);
        List<Faculty> faculties=new ArrayList<Faculty>();
        faculties.add(faculty1);
        faculties.add(faculty2);
        //when
        String url = "http://localhost:" + port + "/faculties";
        ResponseEntity<List<Faculty>> facultyResponseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Faculty>>() {}
        );

        faculties = facultyResponseEntity.getBody();
        //then
        assertNotNull(facultyResponseEntity);   //Получили ответ
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));  //получаем код 200(ответ верный)
        assert faculties != null;
        String w=faculties.get(0).getName();
        assertEquals("TestFaculty1", faculties.get(0).getName());
        Faculty actualFaculty1 = facultyResponseEntity.getBody().get(0);    //получаем тело ответа
        if (actualFaculty1 != null) {
            assertNotNull(actualFaculty1.getId());       //проверяем что у Faculty есть Id
        }
        assert actualFaculty1 != null;
        assertEquals(actualFaculty1.getName(), faculty1.getName());   //проверяем что имя заданое, совпадает с именем возвращенным сервером
        assertThat(actualFaculty1.getColor()).isEqualTo(faculty1.getColor()); //цвет совпадает с возвращенным ответом
    }
    @Test
    public void shouldGetFacultyByColor() {
//given
        Faculty faculty1 = new Faculty("TestFaculty1", "TestColor1");
        Faculty faculty2 = new Faculty("TestFaculty2", "TestColor2");
        faculty1 = facultyRepository.save(faculty1);
        faculty2 = facultyRepository.save(faculty2);
        List<Faculty> faculties=new ArrayList<Faculty>();
        faculties.add(faculty1);
        faculties.add(faculty2);
        //when
        String url = "http://localhost:" + port + "/faculties/color/%7Bcolor%7D?color="+faculty1.getColor();
        ResponseEntity<List<Faculty>> facultyResponseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Faculty>>() {}
        );

        faculties = facultyResponseEntity.getBody();
        //then
        assertNotNull(facultyResponseEntity);   //Получили ответ
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));  //получаем код 200(ответ верный)
        assert faculties != null;
        String w=faculties.get(0).getName();
        assertEquals("TestFaculty1", faculties.get(0).getName());
        Faculty actualFaculty1 = facultyResponseEntity.getBody().get(0);    //получаем тело ответа
        if (actualFaculty1 != null) {
            assertNotNull(actualFaculty1.getId());       //проверяем что у Faculty есть Id
        }
        assert actualFaculty1 != null;
        assertEquals(actualFaculty1.getName(), faculty1.getName());   //проверяем что имя заданое, совпадает с именем возвращенным сервером
        assertThat(actualFaculty1.getColor()).isEqualTo(faculty1.getColor()); //цвет совпадает с возвращенным ответом
    }

    @Test
    public void shouldGetFacultyByColorOrName() {
//given
        Faculty faculty1 = new Faculty("TestFaculty1", "TestColor1");
        Faculty faculty2 = new Faculty("TestFaculty2", "TestColor2");
        Faculty faculty3 = new Faculty("TestFaculty3", "TestColor3");
        faculty1 = facultyRepository.save(faculty1);
        faculty2 = facultyRepository.save(faculty2);
        faculty3 = facultyRepository.save(faculty3);
        List<Faculty> faculties=new ArrayList<Faculty>();
        faculties.add(faculty1);
        faculties.add(faculty2);
        faculties.add(faculty3);
        //when
        String url = "http://localhost:" + port +
                "/faculties/findNameOrColor?name="+faculty1.getName()+"&color="+faculty2.getColor();
        ResponseEntity<List<Faculty>> facultyResponseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Faculty>>() {}
        );

        faculties = facultyResponseEntity.getBody();
        //then
        assertNotNull(facultyResponseEntity);   //Получили ответ
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));  //получаем код 200(ответ верный)
        assert faculties != null;
        String w=faculties.get(0).getName();
        assertEquals("TestFaculty1", faculties.get(0).getName());
        assertEquals("TestColor2", faculties.get(1).getColor());
        Faculty actualFaculty1 = facultyResponseEntity.getBody().get(0);    //получаем тело ответа
        if (actualFaculty1 != null) {
            assertNotNull(actualFaculty1.getId());       //проверяем что у Faculty есть Id
        }
        assert actualFaculty1 != null;
        assertEquals(actualFaculty1.getName(), faculty1.getName());   //проверяем что имя заданое, совпадает с именем возвращенным сервером
        assertThat(actualFaculty1.getColor()).isEqualTo(faculty1.getColor()); //цвет совпадает с возвращенным ответом
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
        String url = "http://localhost:" + port + "/faculties/1/students";
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
    }
}

