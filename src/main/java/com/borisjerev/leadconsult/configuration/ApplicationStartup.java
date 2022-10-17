package com.borisjerev.leadconsult.configuration;

import com.borisjerev.leadconsult.entities.Course;
import com.borisjerev.leadconsult.entities.Student;
import com.borisjerev.leadconsult.entities.Teacher;
import com.borisjerev.leadconsult.entities.TeacherStudent;
import com.borisjerev.leadconsult.enums.CourseTypes;
import com.borisjerev.leadconsult.repositories.CourseRepository;
import com.borisjerev.leadconsult.repositories.StudentRepository;
import com.borisjerev.leadconsult.repositories.TeacherRepository;
import com.borisjerev.leadconsult.repositories.TeacherStudentRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final TeacherStudentRepository teacherStudentRepository;
    private final CourseRepository courseRepository;

    public ApplicationStartup(TeacherRepository teacherRepository,
                              StudentRepository studentRepository,
                              TeacherStudentRepository teacherStudentRepository,
                              CourseRepository courseRepository) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.teacherStudentRepository = teacherStudentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        seedData();
    }

    private void seedData() {
        Course course = new Course();
        course.setName("Computer Science");
        course.setType(CourseTypes.Main);
        courseRepository.save(course);

        course = new Course();
        course.setName("Computer Science");
        course.setType(CourseTypes.Secondary);
        courseRepository.save(course);

        course = new Course();
        course.setName("Engineering");
        course.setType(CourseTypes.Main);
        courseRepository.save(course);

        course = new Course();
        course.setName("Engineering");
        course.setType(CourseTypes.Secondary);
        courseRepository.save(course);


        Teacher teacher = new Teacher();
        teacher.setAge(14);
        teacher.setName("Prof Ivanov");
        teacher = teacherRepository.save(teacher);
        final Teacher teacherToSave = teacher;

        Student student = new Student();
        student.setAge(21);
        student.setName("Ivan");

        Student student2 = new Student();
        student2.setAge(22);
        student2.setName("Asen");

        final List<TeacherStudent> teacherStudents = new ArrayList<>();
        final Set<Student> students =
                new HashSet<>((List<Student>)studentRepository.saveAll(Set.of(student, student2)));
        students.forEach(t -> {
            TeacherStudent teacherStudent = new TeacherStudent();
            teacherStudent.setTeacherId(teacherToSave.getTeacherId());
            teacherStudent.setStudentId(t.getStudentId());
            if (t.getName().equals("Ivan")) {
                teacherStudent.setGroupp("1st");
                teacherStudent.setCourseId(1L);
            } else {
                teacherStudent.setGroupp("2st");
                teacherStudent.setCourseId(2L);
            }
            teacherStudents.add(teacherStudent);
        });
        teacherStudentRepository.saveAll(teacherStudents);


        Student student3 = new Student();
        student3.setAge(23);
        student3.setName("Georgi");
        student3 = studentRepository.save(student3);

        Teacher teacher2 = new Teacher();
        teacher2.setAge(14);
        teacher2.setName("Prof Angelov");
        teacher2 = teacherRepository.save(teacher2);

        TeacherStudent teacherStudent2 = new TeacherStudent();
        teacherStudent2.setTeacherId(teacher2.getTeacherId());
        teacherStudent2.setStudentId(student3.getStudentId());
        teacherStudent2.setCourseId(1L);
        teacherStudent2.setGroupp("1st");

        teacherStudentRepository.save(teacherStudent2);
    }
}
