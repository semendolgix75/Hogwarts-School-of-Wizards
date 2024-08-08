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

@RestController
@RequestMapping("students")
public class StudentController {

    private final StudentService studentService;


    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
//3
    @PostMapping    // GET http://localhost:8080/students
    public Student create(@RequestBody Student student) {
        return studentService.create(student);
    }

    @GetMapping("{id}") // GET http://localhost:8080/students/1
    public ResponseEntity<Student> read(@PathVariable Long id) {
//1
        Student student = studentService.read(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

//4
    @PutMapping("{id}") // PUT http://localhost:8080/students
    public ResponseEntity<Student> update(@RequestBody Student student, @PathVariable Long id) {
        Student editStudent = studentService.update(id, student);
        if (editStudent == null) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(editStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }
//2
    @GetMapping("age/{age}")
    public ResponseEntity<Collection<Student>> getStudentByAge(@RequestParam int age) {

        if (age > 0) {
        return ResponseEntity.ok(studentService.filterByAge(age));
        }
            return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("findStudentAgeBetween")
    public Collection<Student> findAllByAgeBetween(@RequestParam int minAge, @RequestParam int maxAge) {
        return studentService.findAllByAgeBetween(minAge, maxAge);
    }

    @GetMapping("{id}/faculty") // GET http://localhost:8080/students/1
    public Faculty getFaculty(@PathVariable Long id) {
        return studentService.getFaculty(id);
    }
}
