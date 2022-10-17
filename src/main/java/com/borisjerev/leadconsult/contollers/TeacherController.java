package com.borisjerev.leadconsult.contollers;

import com.borisjerev.leadconsult.entities.Teacher;
import com.borisjerev.leadconsult.requests.TeacherDTO;
import com.borisjerev.leadconsult.services.contract.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public List<Teacher> findAllTeachers() {
        return teacherService.findAllTeachers();
    }

    @GetMapping("/{id}")
    public Teacher findByTeacherId(@PathVariable long id) {
        return teacherService.findByTeacherId(id);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody TeacherDTO teacherDto) {
        return new ResponseEntity<>(teacherService.save(teacherDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Teacher update(@PathVariable long id, @RequestBody TeacherDTO teacherDto) {
        return teacherService.update(id, teacherDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        teacherService.delete(id);
    }
}
