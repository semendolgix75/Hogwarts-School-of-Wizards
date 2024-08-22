package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.impl.FacultyServiceImpl;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("faculties")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty create(@RequestBody Faculty faculty) {
        return facultyService.create(faculty);
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> read(@PathVariable Long id) {

        Faculty faculty = facultyService.read(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PutMapping("{id}")
    public ResponseEntity<Faculty> update(@PathVariable Long id, @RequestBody Faculty faculty) {
        Faculty updateFaculty = facultyService.update(id, faculty);
        if (updateFaculty == null) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(updateFaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) {

        facultyService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> getAllFaculty() {
        return ResponseEntity.ok(facultyService.getAllFaculty());
    }

    @GetMapping("color/{color}")
    public ResponseEntity<Collection<Faculty>> filterByColor(@RequestParam String color) {


        if (facultyService.filterByColor(color) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyService.filterByColor(color));
    }

    @GetMapping("findNameOrColor")
    public ResponseEntity<Collection<Faculty>> findAllByNameOrColorIgnoreCase(@RequestParam(required = false) String name,
                                                                              @RequestParam(required = false) String color) {
           return ResponseEntity.ok(facultyService.findAllByNameIgnoreCaseOrColorIgnoreCase(name, color));
        }

@GetMapping("{id}/students")
public List<Student> getStudents(@PathVariable Long id) {
    return facultyService.getStudents(id);
}


}

