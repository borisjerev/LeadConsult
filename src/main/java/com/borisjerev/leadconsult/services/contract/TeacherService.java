package com.borisjerev.leadconsult.services.contract;

import com.borisjerev.leadconsult.entities.Teacher;
import com.borisjerev.leadconsult.requests.TeacherDTO;

import java.util.List;

public interface TeacherService {

    List<Teacher> findAllTeachers();
    Teacher findByTeacherId(long teacherId);
    Long findAllTeachersCount();
    List<Teacher> findAllByCourseAndGroup(Long courseId, String group);
    Teacher save(TeacherDTO teacherDTO);
    Teacher update(long teacherId, TeacherDTO teacherDto);
    void delete(long teacherId);
}
