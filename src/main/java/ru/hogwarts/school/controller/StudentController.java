package ru.hogwarts.school.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("students")
public class StudentController {

    private final StudentService studentService;


    public StudentController(StudentService studentService)

    {
        this.studentService = studentService;
    }
    @PostMapping
    public Student create(@RequestBody Student student) {
        return studentService.create(student);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> read(@PathVariable Long id) {

        Student studentRead = studentService.read(id);
        if (studentRead == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentRead);
    }


    @PutMapping ("{id}")
    public ResponseEntity<Student> update(@PathVariable Long id, @RequestBody Student student) {
        Student editStudent = studentService.update (id,student);
        if (editStudent == null) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
       }
        return ResponseEntity.ok(editStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.ok().build();
    }

   @GetMapping
    public ResponseEntity<Collection<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }
    @GetMapping("age/{age}")
    public ResponseEntity<Collection<Student>> getStudentByAge(@RequestParam int age) {


        if (studentService.filterByAge(age) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentService.filterByAge(age));
    }
}
