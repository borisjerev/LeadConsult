package com.borisjerev.leadconsult.repositories;

import com.borisjerev.leadconsult.entities.Course;
import com.borisjerev.leadconsult.enums.CourseTypes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CourseRepository extends PagingAndSortingRepository<Course, Long> {
    @Query("Select c FROM Course c WHERE c.type = ?1")
    List<Course> findAllByCourse(CourseTypes courseType);
}
