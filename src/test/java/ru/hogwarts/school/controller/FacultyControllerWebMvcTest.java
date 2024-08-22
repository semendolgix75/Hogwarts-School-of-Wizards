package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
class FacultyControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FacultyRepository facultyRepository;
    @Autowired
    private FacultyController facultyController;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FacultyService facultyService;

    @MockBean
    private AvatarService avatarService;

    @Test
    void shouldUpdateFaculty() throws Exception {
        // given
        Long facultyId = 1L;
        Faculty faculty = new Faculty("Test", "TestColor");

        when(facultyService.update(facultyId, faculty)).thenReturn(faculty);

        // when
        ResultActions perform = mockMvc.perform(put("/faculties/{id}", facultyId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(faculty)));

        // then
        perform
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()))
                .andDo(print());
    }
    @Test
    void shouldDeleteFaculty() throws Exception {

       //given
            Long facultyId = 1L;
            Faculty faculty = new Faculty(facultyId,"Test", "TestColor");
            when(facultyService.read(facultyId)).thenReturn(faculty);

            //when
            ResultActions perform = mockMvc.perform(delete("/faculties/{id}", facultyId));

            //then
        perform
                .andExpect(status().isOk())

        ;



        }
    @Test
    void shouldGetFaculty() throws Exception {
        //given
        Long facultyId = 1L;
        Faculty faculty = new Faculty("Test", "TestColor");
        when(facultyService.read(facultyId)).thenReturn(faculty);

        //when
        ResultActions perform = mockMvc.perform(get("/faculties/{id}", facultyId));

        //then
        perform
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()))
                .andDo(print());

    }

    @Test
    void shouldCreateFaculty() throws Exception {
        // given
        Long studentId = 1L;
        Faculty faculty = new Faculty("Ivan", "TestColor");
        Faculty savedFaculty = new Faculty("Ivan", "TestColor");
        savedFaculty.setId(studentId);

        when(facultyService.create(faculty)).thenReturn(savedFaculty);

        // when
        ResultActions perform = mockMvc.perform(post("/faculties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(faculty)));

        // then
        perform
                .andExpect(jsonPath("$.id").value(savedFaculty.getId()))
                .andExpect(jsonPath("$.name").value(savedFaculty.getName()))
                .andExpect(jsonPath("$.color").value(savedFaculty.getColor()))
                .andDo(print());
    }

    @Test
    public void shouldGetAllFaculty() throws Exception {
        // given
        Faculty faculty1 = new Faculty("Name1", "Color1");
        Faculty faculty2 = new Faculty("Name2", "Color2");
        Collection<Faculty> faculties = Arrays.asList(faculty1, faculty2);
        when(facultyService.getAllFaculty()).thenReturn(faculties);
        // when
        mockMvc.perform(get("/faculties"))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(faculty1.getId()))
                .andExpect(jsonPath("$[0].name").value(faculty1.getName()))
                .andExpect(jsonPath("$[0].color").value(faculty1.getColor()))
                .andExpect(jsonPath("$[1].id").value(faculty2.getId()))
                .andExpect(jsonPath("$[1].name").value(faculty2.getName()))
                .andExpect(jsonPath("$[1].color").value(faculty2.getColor()));
    }
    @Test
    public void shouldGetFacultyByColor() throws Exception {

            // given
            String color = "Red";
            Faculty faculty1 = new Faculty("Faculty1", color);
            Faculty faculty2 = new Faculty("Faculty2", color);
            Collection<Faculty> faculties = List.of(faculty1, faculty2);

            // Настройка мока для сервиса
            when(facultyService.filterByColor(color)).thenReturn(faculties);

            // When
            ResponseEntity<Collection<Faculty>> responseEntity = facultyController.filterByColor(color);

            // Then
            assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
            assertThat(responseEntity.getBody()).isEqualTo(faculties);
        }
    @Test
    public void testGetStudentByFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("Faculty 1");

        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Student 1");

        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("Student 2");

        List<Student> students = Arrays.asList(student1, student2);

        Mockito.when(facultyService.getStudents(1L)).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculties/"+faculty.getId()+"/students"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Student 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Student 2"));
    }

}