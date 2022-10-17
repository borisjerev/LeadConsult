package com.borisjerev.leadconsult.repositories;

import com.borisjerev.leadconsult.entities.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {
    @Query("Select s FROM Student s JOIN TeacherStudent ts ON s.studentId = ts.teacherStudentId.studentId " +
            "WHERE ts.teacherStudentId.courseId = ?1")
    List<Student> findAllByCourse(Long courseId);

    @Query("Select s FROM Student s JOIN TeacherStudent ts ON s.studentId = ts.teacherStudentId.studentId " +
            "WHERE ts.teacherStudentId.groupp = ?1")
    List<Student> findAllByGroup(String group);

    @Query("Select s FROM Student s JOIN TeacherStudent ts ON s.studentId = ts.teacherStudentId.studentId " +
            "WHERE s.age > ?1 AND ts.teacherStudentId.courseId = ?2")
    List<Student> findAllByCourseOlderThenAge(Integer age, Long courseId);

    @Query("Select s FROM Student s JOIN TeacherStudent ts ON s.studentId = ts.teacherStudentId.studentId " +
            "WHERE ts.teacherStudentId.courseId = ?1 AND ts.teacherStudentId.groupp = ?2")
    List<Student> findAllByCourseAndGroup(Long courseId, String group);
}
