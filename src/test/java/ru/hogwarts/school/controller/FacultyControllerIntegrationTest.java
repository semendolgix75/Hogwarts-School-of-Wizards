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
import ru.hogwarts.school.repositories.FacultyRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class FacultyControllerIntegrationTest {
    @LocalServerPort
    private int port;
    @Autowired
    private FacultyRepository facultyRepository;
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
        ResponseEntity<Faculty> facultyResponseEntity=restTemplate.exchange("http://localhost:"
                + port + "/faculties/" + faculty.getId(), HttpMethod.PUT,
                HttpEntity,Faculty.class);
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

        ResponseEntity<Faculty> facultyResponseEntity=restTemplate.exchange("http://localhost:"
                        + port + "/faculties/" + faculty.getId(), HttpMethod.DELETE,
                null,Faculty.class);
        //then
        assertNotNull(facultyResponseEntity);   //Получили ответ
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));  //получаем код 200(ответ верный)

        assertThat(facultyRepository.findById(faculty.getId())).isNotPresent(); //Элемент по ID не не найден'

    }
}
