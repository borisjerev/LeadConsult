package com.borisjerev.leadconsult.services.impl;

import com.borisjerev.leadconsult.entities.Student;
import com.borisjerev.leadconsult.repositories.StudentRepository;
import com.borisjerev.leadconsult.repositories.TeacherStudentRepository;
import com.borisjerev.leadconsult.services.contract.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultStudentService implements StudentService {
    private final StudentRepository studentRepository;
    private final TeacherStudentRepository teacherStudentRepository;

    private static final String STUDENT_WITH_ID_NOT_PRESENT = "Student with id[%d] not present";

    public DefaultStudentService(StudentRepository studentRepository,
                                 TeacherStudentRepository teacherStudentRepository) {
        this.studentRepository = studentRepository;
        this.teacherStudentRepository = teacherStudentRepository;
    }

    @Override
    public List<Student> findAllStudents() {
        return (List<Student>) studentRepository.findAll();
    }

    @Override
    public Student findByStudentId(long studentId) {
        final Optional<Student> studentOptional =  studentRepository.findById(studentId);
        if (studentOptional.isEmpty()) {
            throw new IllegalArgumentException(String.format(STUDENT_WITH_ID_NOT_PRESENT, studentId));
        }

        return studentOptional.get();
    }

    @Override
    public Long findAllStudentsCount() {
        return studentRepository.count();
    }

    @Override
    public List<Student> findAllByCourse(Long courseId) {
        return studentRepository.findAllByCourse(courseId);
    }

    @Override
    public List<Student> findAllByGroup(String group) {
        return studentRepository.findAllByGroup(group);
    }

    @Override
    public List<Student> findAllByCourseOlderTheAge(Integer age, Long courseId) {
        return studentRepository.findAllByCourseOlderThenAge(age, courseId);
    }

    @Override
    public List<Student> findAllByCourseAndGroup(Long courseId, String group) {
        return studentRepository.findAllByCourseAndGroup(courseId, group);
    }
}
