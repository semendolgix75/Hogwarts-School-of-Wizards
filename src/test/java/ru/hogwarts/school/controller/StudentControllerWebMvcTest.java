package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private StudentService studentService;

    @MockBean
    private AvatarService avatarService;


    @Test
    void shouldCreateStudent() throws Exception {
        // given
        Long studentId = 1L;
        Student student = new Student("Ivan", 20);
        Student savedStudent = new Student("Ivan", 20);
        savedStudent.setId(studentId);

        when(studentService.create(student)).thenReturn(savedStudent);

        // when
        ResultActions perform = mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));

        // then
        perform
                .andExpect(jsonPath("$.id").value(savedStudent.getId()))
                .andExpect(jsonPath("$.name").value(savedStudent.getName()))
                .andExpect(jsonPath("$.age").value(savedStudent.getAge()))
                .andDo(print());
    }
    @Test
    void shouldGetStudent() throws Exception {
        //given
        Long studentId = 1L;
        Student student = new Student("Test", 20);
        when(studentService.read(studentId)).thenReturn(student);

        //when
        ResultActions perform = mockMvc.perform(get("/students/{id}", studentId));

        //then
        perform
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andDo(print());

    }
    @Test
    void shouldUpdateStudent() throws Exception {
        // given
        Long studentId = 1L;
        Student student = new Student("Ivan", 20);

        when(studentService.update(studentId, student)).thenReturn(student);

        // when
        ResultActions perform = mockMvc.perform(put("/students/{id}", studentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));

        // then
        perform
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andDo(print());
    }
    @Test
    void shouldDeleteStudent() throws Exception {

        //given
        Long studentId = 1L;
        Student student = new Student(studentId,"Test", 20);
        when(studentService.read(studentId)).thenReturn(student);

        //when
        ResultActions perform = mockMvc.perform(delete("/students/{id}", studentId));

        //then
        perform
                .andExpect(status().isOk())

        ;



    }
    @Test
    public void shouldGetAllStudent() throws Exception {
        // given
        Student student1 = new Student("Name1", 20);
        Student student2 = new Student("Name2", 30);
        Collection<Student> students = Arrays.asList(student1, student2);
        when(studentService.getAllStudents()).thenReturn(students);
        // when
        mockMvc.perform(get("/students"))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(student1.getId()))
                .andExpect(jsonPath("$[0].name").value(student1.getName()))
                .andExpect(jsonPath("$[0].age").value(student1.getAge()))
                .andExpect(jsonPath("$[1].id").value(student2.getId()))
                .andExpect(jsonPath("$[1].name").value(student2.getName()))
                .andExpect(jsonPath("$[1].age").value(student2.getAge()));
    }

    @Test
    void testStudentByAge() throws Exception {
        when(studentRepository.findAllByAgeBetween(19, 23))
                .thenReturn(List.of(
                        new Student(1L, "name1", 21),
                        new Student(2L, "name2", 25)));

        mockMvc.perform(MockMvcRequestBuilders.get("/students/byAgeBetween?minAge=19&maxAge=23"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("name1"))
                .andExpect(jsonPath("$[0].age").value(21));

    }


}