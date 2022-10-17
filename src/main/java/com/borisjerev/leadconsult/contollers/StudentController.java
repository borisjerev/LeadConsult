package com.borisjerev.leadconsult.contollers;

import com.borisjerev.leadconsult.entities.Student;
import com.borisjerev.leadconsult.requests.StudentDTO;
import com.borisjerev.leadconsult.services.contract.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> findAllStudents() {
        return studentService.findAllStudents();
    }

    @GetMapping("/{id}")
    public Student findBuTeacherId(@PathVariable long id) {
        return studentService.findByStudentId(id);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody StudentDTO studentDTO) {
        return new ResponseEntity<>(studentService.save(studentDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Student update(@PathVariable long id, @RequestBody StudentDTO studentDTO) {
        return studentService.update(id, studentDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        studentService.delete(id);
    }
}
