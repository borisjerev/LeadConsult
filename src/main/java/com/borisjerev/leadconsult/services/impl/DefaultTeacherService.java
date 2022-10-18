package com.borisjerev.leadconsult.services.impl;

import com.borisjerev.leadconsult.entities.Student;
import com.borisjerev.leadconsult.entities.Teacher;
import com.borisjerev.leadconsult.entities.TeacherStudent;
import com.borisjerev.leadconsult.repositories.StudentRepository;
import com.borisjerev.leadconsult.repositories.TeacherRepository;
import com.borisjerev.leadconsult.repositories.TeacherStudentRepository;
import com.borisjerev.leadconsult.requests.TeacherDTO;
import com.borisjerev.leadconsult.services.contract.TeacherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DefaultTeacherService implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherStudentRepository teacherStudentRepository;
    private final StudentRepository studentRepository;

    private static final String VARIABLES_NOT_GIVEN_WHEN_ASSIGNING =
            "When you assign Student(s) to Teacher course and group, " +
            "'assignedCourse', 'assignedGroup', 'assignedStudents' variables must be given";

    private static final String TEACHER_WITH_ID_NOT_PRESENT = "Teacher with id[%d] not present";

    private static final String NO_STUDENTS_WITH_IDS = "There are no students with ids: %s";


    public DefaultTeacherService(TeacherRepository teacherRepository,
                                 TeacherStudentRepository teacherStudentRepository,
                                 StudentRepository studentRepository) {
        this.teacherRepository = teacherRepository;
        this.teacherStudentRepository = teacherStudentRepository;
        this.studentRepository = studentRepository;
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
            checkAllStudentsArePresent(teacherDTO);
            teacher = teacherRepository.save(teacher);
            saveTeacherStudentRelationship(teacher.getTeacherId(), teacherDTO);

            return teacher;
        } else if (teacherDTO.getAssignedCourse() == null
                    && teacherDTO.getAssignedGroup() == null
                    && (teacherDTO.getAssignedStudents() == null || teacherDTO.getAssignedStudents().isEmpty())) {
            return teacherRepository.save(teacher);
        }

        throw new IllegalArgumentException(VARIABLES_NOT_GIVEN_WHEN_ASSIGNING);
    }

    @Override
    @Transactional
    public Teacher update(long teacherId, TeacherDTO teacherDTO) {
        final Optional<Teacher> teacherOptional = teacherRepository.findById(teacherId);
        if (teacherOptional.isEmpty()) {
            throw new IllegalArgumentException(String.format(TEACHER_WITH_ID_NOT_PRESENT, teacherId));
        }

        final Teacher teacher = Teacher.builder()
                .teacherId(teacherId)
                .age(teacherDTO.getAge())
                .name(teacherDTO.getName())
                .build();

        if (teacherDTO.getAssignedCourse() != null && teacherDTO.getAssignedGroup() != null
                && teacherDTO.getAssignedStudents() != null
                && !teacherDTO.getAssignedStudents().isEmpty()) {
            checkAllStudentsArePresent(teacherDTO);
            deleteRelationship(teacherId);
            saveTeacherStudentRelationship(teacherId, teacherDTO);

            return teacherRepository.save(teacher);
        } else if (teacherDTO.getAssignedCourse() == null
                && teacherDTO.getAssignedGroup() == null
                && (teacherDTO.getAssignedStudents() == null || teacherDTO.getAssignedStudents().isEmpty())) {
            return teacherRepository.save(teacher);
        }

        throw new IllegalArgumentException(VARIABLES_NOT_GIVEN_WHEN_ASSIGNING);
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

    private void checkAllStudentsArePresent(TeacherDTO teacherDTO) {
        final List<Student> presentStudents =
                (List<Student>) studentRepository.findAllById(teacherDTO.getAssignedStudents());
        if (teacherDTO.getAssignedStudents().size() != presentStudents.size()) {
            String[] notPresentedIds = getDifferenceBetween(presentStudents,
                    teacherDTO.getAssignedStudents());

            throw new IllegalArgumentException(String.format(NO_STUDENTS_WITH_IDS,
                    String.join(",", notPresentedIds)));
        }
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

    private String[] getDifferenceBetween(List<Student> presentStudents,
                                          Set<Long> studentIds) {
        final List<Long> presentedStudentsIds =
                presentStudents.stream()
                        .map(Student::getStudentId)
                        .collect(Collectors.toList());

        final List<String> notPresentedIds =
                studentIds.stream()
                        .filter(l -> !presentedStudentsIds.contains(l))
                        .map(l -> l + "")
                        .collect(Collectors.toList());


        return notPresentedIds.toArray(new String[] {});
    }
}
