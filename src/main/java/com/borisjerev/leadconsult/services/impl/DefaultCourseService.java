package com.borisjerev.leadconsult.services.impl;

import com.borisjerev.leadconsult.entities.Course;
import com.borisjerev.leadconsult.enums.CourseTypes;
import com.borisjerev.leadconsult.repositories.CourseRepository;
import com.borisjerev.leadconsult.services.contract.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultCourseService implements CourseService {

    private final CourseRepository courseRepository;

    public DefaultCourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> findAll() {
        return (List<Course>) courseRepository.findAll();
    }

    @Override
    public List<Course> findAllByType(CourseTypes courseType) {
        return courseRepository.findAllByCourse(courseType);
    }
}
