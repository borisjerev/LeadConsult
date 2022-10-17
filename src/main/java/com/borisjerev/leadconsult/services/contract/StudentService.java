package com.borisjerev.leadconsult.services.contract;

import com.borisjerev.leadconsult.entities.Student;
import com.borisjerev.leadconsult.requests.StudentDTO;

import java.util.List;

public interface StudentService {
    List<Student> findAllStudents();
    Student findByStudentId(long studentId);
    Long findAllStudentsCount();
    List<Student> findAllByCourse(Long courseId);
    List<Student> findAllByGroup(String group);
    List<Student> findAllByCourseOlderTheAge(Integer age, Long courseId);
    List<Student> findAllByCourseAndGroup(Long courseId, String group);
    Student save(StudentDTO studentDTO);
    Student update(long studentId, StudentDTO studentDTO);
    void delete(long studentId);
}
