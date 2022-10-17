package com.borisjerev.leadconsult.repositories;

import com.borisjerev.leadconsult.entities.Teacher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends PagingAndSortingRepository<Teacher, Long> {
    @Query("Select t FROM Teacher t JOIN TeacherStudent ts ON t.teacherId = ts.teacherStudentId.teacherId " +
            "WHERE ts.teacherStudentId.courseId = ?1 AND ts.teacherStudentId.groupp = ?2")
    List<Teacher> findAllByCourseAndGroup(Long courseId, String group);
}
