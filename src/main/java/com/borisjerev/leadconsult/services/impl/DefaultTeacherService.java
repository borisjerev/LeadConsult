package com.borisjerev.leadconsult.services.impl;

import com.borisjerev.leadconsult.entities.Teacher;
import com.borisjerev.leadconsult.entities.TeacherStudent;
import com.borisjerev.leadconsult.repositories.TeacherRepository;
import com.borisjerev.leadconsult.repositories.TeacherStudentRepository;
import com.borisjerev.leadconsult.requests.TeacherDTO;
import com.borisjerev.leadconsult.services.contract.TeacherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultTeacherService implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherStudentRepository teacherStudentRepository;

    private static final String VARIABLES_NOT_GIVEN_WHEN_ASSIGNING =
            "When you assign Student to Teacher course and group, " +
            "'assignedCourse', 'assignedGroup', 'assignedStudents' variables must be given";

    private static final String TEACHER_WITH_ID_NOT_PRESENT = "Teacher with id[%d] not present";


    public DefaultTeacherService(TeacherRepository teacherRepository,
                                 TeacherStudentRepository teacherStudentRepository) {
        this.teacherRepository = teacherRepository;
        this.teacherStudentRepository = teacherStudentRepository;
    }

    @Override
    public List<Teacher> findAllTeachers() {
        return (List<Teacher>) teacherRepository.findAll();
    }

    @Override
    public Teacher findByTeacherId(long teacherId) {
        final Optional<Teacher> teacherOptional = teacherRepository.findById(teacherId);
        if (teacherOptional.isEmpty()) {
            throw new IllegalArgumentException(String.format(TEACHER_WITH_ID_NOT_PRESENT, teacherId));
        }

        return teacherOptional.get();
    }

    @Override
    public Long findAllTeachersCount() {
        return teacherRepository.count();
    }

    @Override
    public List<Teacher> findAllByCourseAndGroup(Long courseId, String group) {
        return teacherRepository.findAllByCourseAndGroup(courseId, group);
    }

    @Override
    @Transactional
    public Teacher save(TeacherDTO teacherDTO) {
        Teacher teacher = new Teacher();
        teacher.setName(teacherDTO.getName());
        teacher.setAge(teacherDTO.getAge());

        if (teacherDTO.getAssignedCourse() != null && teacherDTO.getAssignedGroup() != null
                && teacherDTO.getAssignedStudents() != null
                && !teacherDTO.getAssignedStudents().isEmpty()) {
            teacher = teacherRepository.save(teacher);
            saveTeacherStudentRelationship(teacher.getTeacherId(), teacherDTO);

            return teacher;
        } else if (teacherDTO.getAssignedCourse() == null
                    && teacherDTO.getAssignedStudents() == null
                    && (teacherDTO.getAssignedGroup() == null || teacherDTO.getAssignedStudents().isEmpty())) {
            return teacherRepository.save(teacher);
        }

        throw new IllegalArgumentException(VARIABLES_NOT_GIVEN_WHEN_ASSIGNING);
    }

    @Override
    @Transactional
    public Teacher update(long teacherId, TeacherDTO teacherDto) {
        final Optional<Teacher> teacherOptional = teacherRepository.findById(teacherId);
        if (teacherOptional.isEmpty()) {
            throw new IllegalArgumentException(String.format(TEACHER_WITH_ID_NOT_PRESENT, teacherId));
        }

        final Teacher teacher = Teacher.builder()
                .teacherId(teacherId)
                .age(teacherDto.getAge())
                .name(teacherDto.getName())
                .build();

        deleteRelationship(teacherId);
        saveTeacherStudentRelationship(teacherId, teacherDto);

        return teacherRepository.save(teacher);
    }

    @Override
    @Transactional
    public void delete(long teacherId) {
        deleteRelationship(teacherId);
        teacherRepository.deleteById(teacherId);
    }

    private void deleteRelationship(long teacherId) {
        teacherStudentRepository.deleteByTeacherId(teacherId);
    }

    private void saveTeacherStudentRelationship(long teacherId, TeacherDTO teacherDTO) {
        final List<TeacherStudent> teacherStudents = new ArrayList<>();
        teacherDTO.getAssignedStudents().forEach(s -> {
            final TeacherStudent teacherStudent = new TeacherStudent();
            teacherStudent.setTeacherId(teacherId);
            teacherStudent.setStudentId(s);
            teacherStudent.setCourseId(teacherDTO.getAssignedCourse());
            teacherStudent.setGroupp(teacherDTO.getAssignedGroup());
            teacherStudents.add(teacherStudent);
        });
        teacherStudentRepository.saveAll(teacherStudents);
    }
}
