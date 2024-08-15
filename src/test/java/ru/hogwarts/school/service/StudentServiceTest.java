package ru.hogwarts.school.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Nested
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentServiceTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private StudentController studentController;

    @Test
    void contextLoads() throws Exception {
        assertThat(studentController).isNotNull();
    }



@Test
void shouldNotNullPostStudent() throws Exception {
// given
    Student student = new Student();
    student.setId(1L);
    student.setAge(25);
    student.setName("Test");
    String result="http://localhost:" + port + "/student";
// when
// then

    assertThat(
            this.restTemplate.postForObject("http://localhost:" + port + "/student",
                    student, String.class)).isNotNull();
}

    @Test
    void shouldNotNullGetRequestStudent() throws Exception {
// given
        Student student = new Student();
        student.setId(1L);
        student.setAge(25);
        student.setName("Test");
        String result="http://localhost:" + port + "/student";
// when
// then

        assertThat(
                this.restTemplate.postForObject("http://localhost:" + port + "/student",
                        student, String.class)).isNotNull();
    }
    @Test
    void createStudent() {

        Student student = new Student();
        student.setId(152L);
        student.setName("Test");
        student.setAge(20);

        Student result = restTemplate.postForObject("/students", student, Student.class);
        assertEquals(student.getId(), result.getId());
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