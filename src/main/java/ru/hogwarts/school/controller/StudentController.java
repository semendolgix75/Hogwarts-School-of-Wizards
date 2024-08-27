package ru.hogwarts.school.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {

    private final StudentService studentService;


    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping    // GET http://localhost:8080/students
    public Student create(@RequestBody Student student) {
        return studentService.create(student);
    }

    @GetMapping("{id}") // GET http://localhost:8080/students/1
    public ResponseEntity<Student> read(@PathVariable Long id) {

        Student student = studentService.read(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }


    @PutMapping("{id}") // PUT http://localhost:8080/students
    public Student update(@PathVariable Long id,@RequestBody Student student) {

        return studentService.update(id, student);
    }

    @DeleteMapping("{id}")
    public Student delete(@PathVariable Long id) {return studentService.delete(id); }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }


    @GetMapping("byAgeBetween")
    public Collection<Student> findAllByAgeBetween(int minAge, int maxAge) {
        return studentService.findAllByAgeBetween(minAge, maxAge);
    }

    @GetMapping("{id}/faculty") // GET http://localhost:8080/students/{id}/faculty
    public Faculty getFaculty(@PathVariable Long id) {
        return studentService.getFaculty(id);
    }

    @GetMapping("countStudent") // GET http://localhost:8080/getCountStudent
    public Integer getCountAllStudent() {
        return studentService.getAllStudentCount();
    }

    @GetMapping("averageAgeStudent") // GET http://localhost:8080/getCountStudent
    public Double getAverageAgeStudent() {
        return studentService.getAverageAgeStudent();
    }

    @GetMapping("lastFiveByIdStudent") // GET http://localhost:8080/getCountStudent
    public List<Student> getLastFiveByIdStudent() {
        return studentService.getLastFiveByIdStudent();
    }
}
