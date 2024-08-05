package ru.hogwarts.school.service.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FacultyServiceImplTest {
    @Mock
    FacultyRepository mockRepository;
    @InjectMocks
    FacultyService facultyService;

    Faculty faculty1 = new Faculty(1L, "history", "red");
    Faculty faculty2 = new Faculty(2L, "history", "pink");
    Faculty faculty3 = new Faculty(3L, "math", "red");
    List<Faculty> faculty = new ArrayList<>(List.of(faculty1, faculty2, faculty3));
    List<Faculty> facultyRed = new ArrayList<>(List.of(faculty1, faculty3));

    @Test
    void createFacultyByPostegree() {
        when(mockRepository.save(new Faculty(1L,
                "history", "red")))
                .thenReturn(faculty1);
        assertEquals(faculty1, facultyService.create(new Faculty(1L,
                "history", "red")));
    }

    @Test
    void findFacultyByPostegree() {
        when(mockRepository.findAll())
                .thenReturn(faculty);
    }
}