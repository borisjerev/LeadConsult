package com.borisjerev.leadconsult.contollers;

import com.borisjerev.leadconsult.entities.Student;
import com.borisjerev.leadconsult.services.contract.StudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
