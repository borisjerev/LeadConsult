package com.borisjerev.leadconsult.contollers;

import com.borisjerev.leadconsult.entities.Course;
import com.borisjerev.leadconsult.services.contract.CourseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> findAll() {
        return courseService.findAll();
    }

    @GetMapping("/{id}")
    public Course findByTeacherId(@PathVariable long id) { return courseService.findById(id); }
}
