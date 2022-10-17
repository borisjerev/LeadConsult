package com.borisjerev.leadconsult.services.impl;

import com.borisjerev.leadconsult.entities.Course;
import com.borisjerev.leadconsult.enums.CourseTypes;
import com.borisjerev.leadconsult.repositories.CourseRepository;
import com.borisjerev.leadconsult.services.contract.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultCourseService implements CourseService {

    private final CourseRepository courseRepository;

    private static final String COURSE_WITH_ID_NOT_PRESENT = "Course with id[%d] not present";

    public DefaultCourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> findAll() {
        return (List<Course>) courseRepository.findAll();
    }

    @Override
    public Course findById(long courseId) {
        final Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty()) {
            throw new IllegalArgumentException(String.format(COURSE_WITH_ID_NOT_PRESENT, courseId));
        }

        return courseOptional.get();
    }

    @Override
    public List<Course> findAllByType(CourseTypes courseType) {
        return courseRepository.findAllByCourse(courseType);
    }
}
