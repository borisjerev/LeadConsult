package com.borisjerev.leadconsult.services.impl;

import com.borisjerev.leadconsult.entities.Student;
import com.borisjerev.leadconsult.entities.TeacherStudent;
import com.borisjerev.leadconsult.repositories.StudentRepository;
import com.borisjerev.leadconsult.repositories.TeacherStudentRepository;
import com.borisjerev.leadconsult.requests.StudentDTO;
import com.borisjerev.leadconsult.services.contract.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultStudentService implements StudentService {
    private final StudentRepository studentRepository;
    private final TeacherStudentRepository teacherStudentRepository;

    private static final String STUDENT_WITH_ID_NOT_PRESENT = "Student with id[%d] not present";
    private static final String VARIABLES_NOT_GIVEN_WHEN_ASSIGNING =
            "When you assign Teacher(s) to Student course and group, " +
                    "'assignedCourse', 'assignedGroup', 'assignedTeachers' variables must be given";

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

    @Override
    @Transactional
    public Student save(StudentDTO studentDTO) {
        Student student = new Student();
        student.setName(studentDTO.getName());
        student.setAge(studentDTO.getAge());

        if (studentDTO.getAssignedCourse() != null && studentDTO.getAssignedGroup() != null
                && studentDTO.getAssignedTeacher() != null) {
            student = studentRepository.save(student);
            saveTeacherStudentRelationship(student.getStudentId(), studentDTO);

            return student;
        } else if (studentDTO.getAssignedCourse() == null
                && studentDTO.getAssignedGroup() == null
                && studentDTO.getAssignedTeacher() == null) {
            return studentRepository.save(student);
        }

        throw new IllegalArgumentException(VARIABLES_NOT_GIVEN_WHEN_ASSIGNING);
    }

    @Override
    @Transactional
    public Student update(long studentId, StudentDTO studentDTO) {
        final Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isEmpty()) {
            throw new IllegalArgumentException(String.format(STUDENT_WITH_ID_NOT_PRESENT, studentId));
        }

        final Student student = Student.builder()
                .studentId(studentId)
                .age(studentDTO.getAge())
                .name(studentDTO.getName())
                .build();

        if (studentDTO.getAssignedCourse() != null && studentDTO.getAssignedGroup() != null
                && studentDTO.getAssignedTeacher() != null) {
            deleteRelationship(studentId);
            saveTeacherStudentRelationship(studentId, studentDTO);

            return studentRepository.save(student);
        } else if (studentDTO.getAssignedCourse() == null
                && studentDTO.getAssignedGroup() == null
                && studentDTO.getAssignedTeacher() == null) {
            return studentRepository.save(student);
        }

        throw new IllegalArgumentException(VARIABLES_NOT_GIVEN_WHEN_ASSIGNING);
    }

    @Override
    @Transactional
    public void delete(long studentId) {
        deleteRelationship(studentId);
        studentRepository.deleteById(studentId);
    }

    private void deleteRelationship(long studentId) {
        teacherStudentRepository.deleteByStudentId(studentId);
    }

    private void saveTeacherStudentRelationship(long studentId, StudentDTO studentDTO) {
        final List<TeacherStudent> teacherStudents = new ArrayList<>();

        final TeacherStudent teacherStudent = new TeacherStudent();
        teacherStudent.setStudentId(studentId);
        teacherStudent.setTeacherId(studentDTO.getAssignedTeacher());
        teacherStudent.setCourseId(studentDTO.getAssignedCourse());
        teacherStudent.setGroupp(studentDTO.getAssignedGroup());
        teacherStudents.add(teacherStudent);

        teacherStudentRepository.saveAll(teacherStudents);
    }
}
