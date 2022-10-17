package com.borisjerev.leadconsult.contollers;

import com.borisjerev.leadconsult.entities.Student;
import com.borisjerev.leadconsult.enums.CourseTypes;
import com.borisjerev.leadconsult.responses.TeacherStudentDTO;
import com.borisjerev.leadconsult.services.contract.CourseService;
import com.borisjerev.leadconsult.services.contract.StudentService;
import com.borisjerev.leadconsult.services.contract.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/reports")
public class ReportController {

    private final TeacherService teacherService;
    private final StudentService studentService;
    private final CourseService courseService;

    public ReportController(TeacherService teacherService,
                            StudentService studentService,
                            CourseService courseService) {
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    //teachers
    @GetMapping("/teachersCount")
    public Long findAllTeachersCount() {
        return teacherService.findAllTeachersCount();
    }


    //students
    @GetMapping("/studentsCount")
    public Long findAllStudentsCount() {
        return studentService.findAllStudentsCount();
    }

    @GetMapping("/studentsByCourse")
    public List<Student> findAllStudentsByCourse(@RequestParam Long courseId) {
        return studentService.findAllByCourse(courseId);
    }

    @GetMapping("/studentsByGroup")
    public List<Student> findAllStudentsByGroup(@RequestParam String group) {
        return studentService.findAllByGroup(group);
    }

    @GetMapping("/studentsByCourseAndOlderThenAge")
    public List<Student> findAllStudentsByOlderThenAgeAndCourse(@RequestParam Integer age,
                                                                    @RequestParam Long courseId) {
        return studentService.findAllByCourseOlderTheAge(age, courseId);
    }

    //teachers and students
    @GetMapping("/teachersAndStudentsByCourseAndGroup")
    public TeacherStudentDTO findAllTeachersAndStudentsByGroupAndCourse(@RequestParam Long courseId,
                                                                        @RequestParam String group) {
        final TeacherStudentDTO teacherStudentDTO = new TeacherStudentDTO();
        teacherStudentDTO.setTeachers(teacherService.findAllByCourseAndGroup(courseId, group));
        teacherStudentDTO.setStudents(studentService.findAllByCourseAndGroup(courseId, group));

        return teacherStudentDTO;
    }

    // courses
    @GetMapping("/coursesByType")
    public ResponseEntity<?> findAllCoursesByType(@RequestParam String courseTypeName) {
        courseTypeName = courseTypeName.substring(0, 1).toUpperCase() +
                courseTypeName.substring(1).toLowerCase();
        final CourseTypes courseType = getCourseForName(courseTypeName);
        if (courseType == null) {
            return new ResponseEntity<>("We have only two types: " + CourseTypes.Main  + " AND " + CourseTypes.Secondary,
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(courseService.findAllByType(courseType), HttpStatus.OK);
    }

    private CourseTypes getCourseForName(String name) {
        return CourseTypes.findByName(name);
    }
}
