package com.borisjerev.leadconsult.services.contract;

import com.borisjerev.leadconsult.entities.Course;
import com.borisjerev.leadconsult.enums.CourseTypes;

import java.util.List;

public interface CourseService {
    List<Course> findAll();
    List<Course> findAllByType(CourseTypes courseType);
}
